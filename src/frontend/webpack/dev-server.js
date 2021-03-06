'use strict';

module.exports = {
    contentBase: './src',
    host: '0.0.0.0',
    public: '192.168.0.7',
    port: 9000,
    inline: true,
    historyApiFallback: true,
    stats: 'errors-only',
    watchOptions: {
        aggregateTimeout: 300,
        poll: 500
    }
};
