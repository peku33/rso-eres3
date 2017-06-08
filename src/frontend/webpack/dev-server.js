'use strict';

module.exports = {
    contentBase: './src',
    host: "0.0.0.0",
    port: 9000,
    inline: true,
    historyApiFallback: true,
    stats: 'errors-only',
    disableHostCheck: true,
    watchOptions: {
        aggregateTimeout: 300,
        poll: 500
    }
};
