"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = ErrorPage;
var _reactRouterDom = require("react-router-dom");
function ErrorPage() {
  var error = (0, _reactRouterDom.useRouteError)();
  console.error(error);
  return /*#__PURE__*/React.createElement("div", {
    id: "error-page"
  }, /*#__PURE__*/React.createElement("h1", null, "Oops!"), /*#__PURE__*/React.createElement("p", null, "Sorry, an unexpected error has occurred."), /*#__PURE__*/React.createElement("p", null, "404!"));
}