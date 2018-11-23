// @ts-check

/**
 * @param {import('webpack').Configuration} config
 * @param {"development" | "production"} env
 */
// module.exports = function override(config, env) {
//     // overwrite webpack config to add css modules using react app rewired
//
//     // find node for css loader
//     let use = config.module.rules
//         .find(x => "oneOf" in x)
//         .oneOf.find(
//             x =>
//                 x.use &&
//                 x.use[1] &&
//                 x.use[1].loader &&
//                 x.use[1].loader.includes("/css-loader/"),
//         ).use[1];
//
//     // config for css modules
//     use.options = {
//         ...use.options,
//         modules: true,
//         localIdentName: "[name]__[local]--[hash:base64:5]",
//         sourceMap: true,
//         camelCase: true,
//     };
//
//     return config;
// };
