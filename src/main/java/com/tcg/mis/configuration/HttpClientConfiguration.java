package com.tcg.mis.configuration;


import com.tcg.mis.common.http.HttpClientTool;
import com.tcg.mis.common.log.TcgLogFactory;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

/**
 * Description: http client <br/>
 *
 * @author Eddie
 */
@Configuration
@EnableScheduling
public class HttpClientConfiguration {

    private static final Logger LOGGER = TcgLogFactory.getLogger(HttpClientConfiguration.class);

    // Determines the timeout in milliseconds until a connection is established.
    private static final int CONNECT_TIMEOUT = 10000;
    // Returns the timeout in milliseconds used when requesting a connection from the connection manager.
    private static final int REQUEST_TIMEOUT = 10000;
    // Defines the socket timeout (SO_TIMEOUT) in milliseconds, which is the timeout for waiting for data or,
    // put differently, a maximum period inactivity between two consecutive data packets).
    private static final int SOCKET_TIMEOUT = 60000;

    private static final int DEFAULT_MAX_PER_ROUTE = 20;
    private static final int MAX_TOTAL_CONNECTIONS = 20;
    private static final int ONE_SECOND_IN_MILLIS = 1000;
    private static final int DEFAULT_KEEP_ALIVE_TIME_MILLIS = 5 * ONE_SECOND_IN_MILLIS;
    private static final int CLOSE_IDLE_CONNECTION_WAIT_TIME_SECS = 30;

    @Bean
    public PoolingHttpClientConnectionManager getPoolingConnectionManager() {
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        poolingConnectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        poolingConnectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
        return poolingConnectionManager;
    }

    @Bean
    public Runnable getIdleConnectionMonitor(final PoolingHttpClientConnectionManager connectionManager) {
        return new Runnable() {
            @Override
            @Scheduled(fixedDelay = 10000)
            public void run() {
                try {
                    if (connectionManager != null) {
                        LOGGER.trace("run IdleConnectionMonitor - Closing expired and idle connections...");
                        connectionManager.closeExpiredConnections();
                        connectionManager.closeIdleConnections(CLOSE_IDLE_CONNECTION_WAIT_TIME_SECS, TimeUnit.SECONDS);
                    } else {
                        LOGGER.trace("run IdleConnectionMonitor - Http Client Connection manager is not initialised");
                    }
                } catch (Exception e) {
                    LOGGER.error("run IdleConnectionMonitor - Exception occurred. msg={}, e={}", e.getMessage(), e);
                }
            }

        };
    }

    @Bean
    public ConnectionKeepAliveStrategy getConnectionKeepAliveStrategy() {
        return new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                HeaderElementIterator it = new BasicHeaderElementIterator
                        (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();

                    if (value != null && "timeout".equalsIgnoreCase(param)) {
                        return Long.parseLong(value) * ONE_SECOND_IN_MILLIS;
                    }
                }
                return DEFAULT_KEEP_ALIVE_TIME_MILLIS;
            }
        };
    }

    @Bean
    public CloseableHttpClient getHttpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                                                   .setConnectionRequestTimeout(REQUEST_TIMEOUT)
                                                   .setConnectTimeout(CONNECT_TIMEOUT)
                                                   .setSocketTimeout(SOCKET_TIMEOUT).build();

        return HttpClients.custom()
                          .setDefaultRequestConfig(requestConfig)
                          .setConnectionManager(getPoolingConnectionManager())
                          .setKeepAliveStrategy(getConnectionKeepAliveStrategy())
                          .build();
    }

    @Bean
    public HttpClientTool getHttpClientTool() {
        return new HttpClientTool();
    }

}