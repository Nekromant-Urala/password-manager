package com.ural.manager.serialization;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JsonFileWatcher {
    private final Path filePath;
    private final Runnable onChangeCallback;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private WatchService watchService;
    private volatile boolean isRunning = true;

    public JsonFileWatcher(String filePath, Runnable onChangeCallback) {
        this.filePath = Paths.get(filePath);
        this.onChangeCallback = onChangeCallback;
    }

    public void startWatching() {
        executor.submit(() -> {
            try {
                watchService = FileSystems.getDefault().newWatchService();
                Path parentDir = filePath.getParent();
                parentDir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

                while (isRunning) {
                    try {
                        WatchKey key = watchService.take();
                        for (WatchEvent<?> event : key.pollEvents()) {
                            Path changedFile = (Path) event.context();
                            if (changedFile.equals(filePath.getFileName())) {
                                onChangeCallback.run();
                            }
                        }
                        key.reset();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeWatchService();
            }
        });
    }

    public void stopWatching() {
        isRunning = false;
        executor.shutdownNow();
        closeWatchService();
    }

    private void closeWatchService() {
        try {
            if (watchService != null) {
                watchService.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
