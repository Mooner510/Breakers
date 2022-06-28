package org.mooner.backedbreaker;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncConnection {
    private static final AtomicInteger postThread = new AtomicInteger();
    private static final AtomicInteger getThread = new AtomicInteger();
    private static final AtomicInteger getSearchThread = new AtomicInteger();
    private static final AtomicInteger postCount = new AtomicInteger();
    private static final AtomicInteger getCount = new AtomicInteger();
    private static final AtomicInteger getSearchCount = new AtomicInteger();

    public int getPostCount() {
        return postCount.get();
    }

    public int getGetCount() {
        return getCount.get();
    }

    public int getSearchCount() {
        return getSearchCount.get();
    }

    public void createPostThread() {
        int id = postThread.getAndIncrement();
//        threads.put(asyncInt.getAndIncrement(), new Thread(() -> {
//            while (true) post();
//        }));
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException ignore) {
                }
                post();
                postCount.incrementAndGet();
            }
        }).start();
    }

    public void createGetThread() {
        int id = getThread.getAndIncrement();
//        threads.put(asyncInt.get(), new Thread(() -> {
//            while (true) get();
//        }));
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException ignore) {
                }
                get();
                getCount.incrementAndGet();
            }
        }).start();
    }

    public void createGetSearchThread() {
        int id = getSearchThread.getAndIncrement();
//        threads.put(asyncInt.get(), new Thread(() -> {
//            while (true) get();
//        }));
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException ignore) {
                }
                getSearch();
                getCount.incrementAndGet();
            }
        }).start();
    }

    public void createDeleteThread() {
        int id = getSearchThread.getAndIncrement();
//        threads.put(asyncInt.get(), new Thread(() -> {
//            while (true) get();
//        }));
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException ignore) {
                }
                delete();
                getCount.incrementAndGet();
            }
        }).start();
    }

    private static String gen(int count) {
        return RandomStringUtils.randomAlphanumeric(count);
    }

    private static String getRandomPostData() {
        return String.format("{\"address\":\"%s\",\"category\":\"%s\",\"homePageLink\":\"%s\",\"imageLink\":\"%s\",\"roadAddress\":\"%s\",\"title\":\"%s\"}", gen(1000), gen(1000), gen(1000), gen(1000), gen(1000), gen(1000));
    }

    private static void get() {
        try {
            URL url2 = new URL("http://10.156.147.167:9090/api/restaurant/all");
            HttpURLConnection get = (HttpURLConnection) url2.openConnection();
            get.setRequestMethod("GET");
            get.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            get.setConnectTimeout(5000);
            get.setReadTimeout(5000);

            new BufferedReader(new InputStreamReader(get.getInputStream(), StandardCharsets.UTF_8)).close();
        } catch (IOException ignore) {
//            throw new RuntimeException(e);
        }
    }

    private static void getSearch() {
        try {
            URL url2 = new URL("http://10.156.147.167:9090/api/restaurant/search?matjip=" + gen(12));
            HttpURLConnection get = (HttpURLConnection) url2.openConnection();
            get.setRequestMethod("GET");
            get.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            get.setConnectTimeout(5000);
            get.setReadTimeout(5000);

            new BufferedReader(new InputStreamReader(get.getInputStream(), StandardCharsets.UTF_8)).close();
        } catch (IOException ignore) {
//            throw new RuntimeException(e);
        }
    }

    private static void post() {
        try {
            URL url = new URL("http://10.156.147.167:9090/api/restaurant");
            HttpURLConnection post = (HttpURLConnection) url.openConnection();
            post.setRequestMethod("POST");
            post.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            post.setConnectTimeout(5000);
            post.setReadTimeout(5000);
            post.setDoOutput(true);

            try(OutputStream o = post.getOutputStream()) {
                o.write(getRandomPostData().getBytes(StandardCharsets.UTF_8));
                o.flush();
            } catch (IOException ignore) {
            }
            new BufferedReader(new InputStreamReader(post.getInputStream(), StandardCharsets.UTF_8)).close();
        } catch (IOException ignore) {
        }
    }

    private static void delete() {
        try {
            URL url = new URL("http://10.156.147.167:9090/api/restaurant/delete-all");
            HttpURLConnection post = (HttpURLConnection) url.openConnection();
            post.setRequestMethod("DELETE");
            post.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            post.setConnectTimeout(5000);
            post.setReadTimeout(5000);
            new BufferedReader(new InputStreamReader(post.getInputStream(), StandardCharsets.UTF_8)).close();
        } catch (IOException ignore) {
        }
    }
}
