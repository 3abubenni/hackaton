"use strict";

var _react = _interopRequireDefault(require("react"));
var _client = _interopRequireDefault(require("react-dom/client"));
var _App = _interopRequireDefault(require("../../App/App"));
var _reactRouterDom = require("react-router-dom");
var _ErrorPage = _interopRequireDefault(require("../../ErrorPage/ErrorPage"));
function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
var router = (0, _reactRouterDom.createBrowserRouter)([{
  path: "*",
  element: /*#__PURE__*/_react.default.createElement(_App.default, null),
  errorElement: /*#__PURE__*/_react.default.createElement(_ErrorPage.default, null)
}]);
_client.default.createRoot(document.getElementById('root')).render( /*#__PURE__*/_react.default.createElement(_react.default.StrictMode, null, /*#__PURE__*/_react.default.createElement(_reactRouterDom.RouterProvider, {
  router: router
})));