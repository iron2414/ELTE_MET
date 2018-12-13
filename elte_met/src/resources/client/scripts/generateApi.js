// @ts-check

var fs = require("fs");
var CodeGen = require("swagger-typescript-codegen").CodeGen;

var file = "swagger/spec.json";
var swagger = JSON.parse(fs.readFileSync(file, "UTF-8"));
var tsSourceCode = CodeGen.getTypescriptCode({
    className: "BackendApi",
    swagger: swagger,
    // imports: ["../../typings/tsd.d.ts"]
});
// console.log(tsSourceCode);
fs.writeFileSync("src/api/automatic/BackendApi.ts", tsSourceCode, {
    encoding: "utf8"
});
