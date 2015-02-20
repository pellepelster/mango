var proxyServer = require('http-route-proxy');

/**
 *   proxy configs
 */
proxyServer.proxy([
    // common config
    {
        // origin host + port
        from: 'localhost:9000',
        
        // forward host + port
        to: 'localhost:9090',
        // match forward action rule
        // `"/"` means forward match all actions, 
        // `!/public` means match local static forward match `/public.*`

        route: [ '/remote', '!/Project1AdminClient']
        
    }
]);
