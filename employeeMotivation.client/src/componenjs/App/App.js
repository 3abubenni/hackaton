"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = void 0;
var _Auth = require("../Auth/Parent/app/Auth");
var _reactRouterDom = require("react-router-dom");
var _Reg = require("../Reg/Parent/app/Reg");
var _Workzone = require("../Workzone/Parent/app/Workzone");
var _ErrorPage = _interopRequireDefault(require("../ErrorPage/ErrorPage"));
function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
function App() {
  return /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement(_reactRouterDom.Routes, null, /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    index: true,
    element: /*#__PURE__*/React.createElement(_Auth.Auth, null)
  }), /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    path: "/auth",
    element: /*#__PURE__*/React.createElement(_Auth.Auth, null)
  }), /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    path: "/reg",
    element: /*#__PURE__*/React.createElement(_Reg.Reg, null)
  }), /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    path: "/workzone",
    element: /*#__PURE__*/React.createElement(_Workzone.Workzone, null)
  }, /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    path: "tasks"
  }), /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    path: "clans"
  }), /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    path: "store"
  }), /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    path: "profile"
  }), /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    path: "inventory"
  }), /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    path: "usersTasks"
  }), /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    path: "notifications"
  }), /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    path: "userClan"
  })), /*#__PURE__*/React.createElement(_reactRouterDom.Route, {
    path: "/*",
    element: /*#__PURE__*/React.createElement(_ErrorPage.default, null)
  })));
}
var _default = exports.default = App;