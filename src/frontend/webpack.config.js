var webpack = require('webpack');
var path = require('path');
var CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports =
{
	entry:
	{
		'polyfills': './polyfills.ts',
		'vendor': './vendor.ts',
		'app': './src/main.ts',
	},
	output:
	{
		path: path.resolve(__dirname, './dist'),
		filename: '[name].js'
	},
	resolve:
	{
    	extensions: ['.ts', '.js']
  	},
  	module:
  	{
		loaders:
		[
		  	{
				test: /\.ts$/,
				loaders: ['awesome-typescript-loader', 'angular2-template-loader']
			},
			{ 
				test: /\.(html|css)$/, 
				loader: 'raw-loader'
			}
		]
  	},
	plugins:
	[
		/*new webpack.optimize.UglifyJsPlugin(
		{
			compress:
			{
				warnings: false
			}
		}),*/
		new webpack.optimize.CommonsChunkPlugin(
		{
			name: ['app', 'vendor', 'polyfills']
		}),
		new CopyWebpackPlugin(
		[
			{ from: 'src/index.html' },
		])
	]
};