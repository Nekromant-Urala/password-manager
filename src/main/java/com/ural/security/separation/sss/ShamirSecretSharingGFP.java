package com.ural.security.separation.sss;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShamirSecretSharingGFP {
    private static final BigInteger PRIME = new BigInteger(512, 1, new SecureRandom());
    private static final SecureRandom random = new SecureRandom();
    private final int minimumKey;
    private final int shares;

    public ShamirSecretSharingGFP(int minimumKey, int shares) {
        this.minimumKey = minimumKey;
        this.shares = shares;
    }

    /**
     * Генерирует случайные доли для заданного секрета.
     * Создает случайный полином степени (minimum - 1), где константный член - это секрет.
     *
     * @param secret "секрет", значение которое необходимо разбить
     * @return возвращает List точек (x, y) - долей секрета.
     */
    public ArrayList<BigInteger[]> split(String secret) {
        if (minimumKey > shares) {
            // Если минимальное необходимое количество долей больше, чем общее количество долей,
            // секрет не может быть восстановлен. Выбрасываем исключение.
            throw new IllegalArgumentException("Секрет не может быть восстановлен.");
        }
        // Генерируем случайные коэффициенты полинома.
        // Первый коэффициент - это секрет.
        ArrayList<BigInteger> poly = new ArrayList<>(minimumKey);
        BigInteger secretValue = new BigInteger(secret.getBytes(StandardCharsets.UTF_8));
        if (secretValue.compareTo(PRIME) >= 0){
            throw new IllegalArgumentException("Секрет слишком большой. Необходимо выбрать большее значение PRIME.");
        }
        poly.add(secretValue);

        // Генерируем случайные коэффициенты для остальных членов.
        for (int i = 1; i < minimumKey; i++) {
            poly.add(generate());
        }

        // Генерируем точки долей, вычисляя значение полинома в разных точках x.
        ArrayList<BigInteger[]> points = new ArrayList<>(shares);
        for (int i = 1; i <= shares; i++) {
            BigInteger x = BigInteger.valueOf(i);   // координата x
            BigInteger y = eval(poly, x); // y = f(x) mod PRIME
            // Сохраняем долю как пару (x, y).
            points.add(new BigInteger[]{new BigInteger(x.toByteArray()), y});
        }
        return points;
    }

    /**
     * Восстанавливает секрет из подмножества долей.
     *
     * @param shares список, ключей который передается для восстановления "секрета"
     * @return возвращает значение "секрета"
     */
    public String join(List<BigInteger[]> shares) {
        if (shares.size() < minimumKey) {
            // Необходимо как минимум 'minimum' количество долей для восстановления секрета.
            throw new IllegalArgumentException("Необходимо больше долей для восстановления.");
        }

        // Извлекаем координаты x и y из долей.
        List<BigInteger> x_s = new ArrayList<>();
        List<BigInteger> y_s = new ArrayList<>();
        for (BigInteger[] share : shares) {
            x_s.add(share[0]); // координата x
            y_s.add(share[1]); // координата y
        }

        // Выполняем интерполяцию Лагранжа в точке x = 0, чтобы найти секрет.
        return new String(lagrangeInterpolate(x_s, y_s).toByteArray(), StandardCharsets.UTF_8);
    }

    /**
     * Генерирует криптографически безопасное случайное целое число в диапазоне [0, max).
     * Использует SecureRandom для безопасной генерации случайных чисел.
     *
     * @return случайное целое число в диапазоне [0, max)
     */
    private static BigInteger generate() {
        BigInteger n;
        do {
            n = new BigInteger(ShamirSecretSharingGFP.PRIME.bitLength(), random);
        } while (n.compareTo(ShamirSecretSharingGFP.PRIME) >= 0); // Убеждаемся, что n < max
        return n;
    }

    /**
     * Вычисляет значение полинома в заданной точке x по модулю prime.
     *
     * @param poly List коэффициентов полинома
     * @param x    координата точки
     * @return -
     */
    private static BigInteger eval(List<BigInteger> poly, BigInteger x) {
        BigInteger accum = BigInteger.ZERO; // Инициализируем аккумулятор нулем
        // Вычисляем полином, используя метод Горнера для эффективности.
        // Начинаем с члена наивысшей степени и двигаемся в обратном порядке.
        for (int i = poly.size() - 1; i >= 0; i--) {
            accum = accum.multiply(x).mod(PRIME);       // accum = (accum * x) % prime
            accum = accum.add(poly.get(i)).mod(PRIME); // accum = (accum + poly[i]) % prime
        }
        // Возвращаем копию аккумулятора, чтобы избежать непреднамеренных изменений.
        return accum;
    }

    private static BigInteger divmod(BigInteger num, BigInteger den) {
        BigInteger inv = den.modInverse(PRIME);
        return num.multiply(inv).mod(PRIME);
    }

    /**
     * Выполняет интерполяцию Лагранжа в заданной точке x.
     * Восстанавливает исходный полином, используя предоставленные точки (x_s, y_s)
     * и вычисляет его значение в x. В этом случае мы обычно устанавливаем x = 0, чтобы найти секрет.
     *
     * @param x_s координата точки восстановления
     * @param y_s координата точки восстановления
     * @return Интерполированное значение в точке x
     */
    private static BigInteger lagrangeInterpolate(List<BigInteger> x_s, List<BigInteger> y_s) {
        int k = x_s.size(); // Количество точек/долей

        // Убеждаемся, что все координаты x различны.
        Map<String, Boolean> x_s_set = new HashMap<>();
        for (BigInteger xi : x_s) {
            String key = xi.toString();
            if (x_s_set.containsKey(key)) {
                throw new IllegalArgumentException("Точки должны быть различными");
            }
            x_s_set.put(key, true);
        }

        // Подготавливаем массивы для числителей и знаменателей.
        List<BigInteger> nums = new ArrayList<>(k); // Числители
        List<BigInteger> dens = new ArrayList<>(k); // Знаменатели

        // Вычисляем базисные полиномы Лагранжа.
        for (int i = 0; i < k; i++) {
            BigInteger num_accum = BigInteger.ONE; // Аккумулятор числителя
            BigInteger den_accum = BigInteger.ONE; // Аккумулятор знаменателя

            for (int j = 0; j < k; j++) {
                if (i == j) {
                    continue; // Пропускаем текущий член
                }
                BigInteger xi = x_s.get(i);
                BigInteger xj = x_s.get(j);

                // Вычисляем числитель: num_accum *= (x - xj) % p
                BigInteger tempNum = BigInteger.ZERO.subtract(xj).mod(PRIME);
                num_accum = num_accum.multiply(tempNum).mod(PRIME);

                // Вычисляем знаменатель: den_accum *= (xi - xj) % p
                BigInteger tempDen = xi.subtract(xj).mod(PRIME);
                den_accum = den_accum.multiply(tempDen).mod(PRIME);
            }
            nums.add(num_accum); // Сохраняем числитель для члена i
            dens.add(den_accum); // Сохраняем знаменатель для члена i
        }

        BigInteger result = BigInteger.ZERO; // Инициализируем результат

        // Объединяем члены для вычисления окончательного значения.
        for (int i = 0; i < k; i++) {
            BigInteger term = divmod(nums.get(i), dens.get(i));
            term = term.multiply(y_s.get(i)).mod(PRIME);
            result = result.add(term).mod(PRIME);
        }
        return result;
    }
}
