"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.SignUp = void 0;
var _react = _interopRequireDefault(require("react"));
var _reactRouterDom = require("react-router-dom");
function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
var SignUp = exports.SignUp = function SignUp() {
  var navigate = (0, _reactRouterDom.useNavigate)();
  var ToLogIn = function ToLogIn() {
    navigate("/auth");
  };
  return /*#__PURE__*/_react.default.createElement("div", {
    id: "container"
  }, /*#__PURE__*/_react.default.createElement("h1", null, "Registration"), /*#__PURE__*/_react.default.createElement("input", {
    type: "text",
    placeholder: "Email"
  }), /*#__PURE__*/_react.default.createElement("input", {
    type: "password",
    placeholder: "Password"
  }), /*#__PURE__*/_react.default.createElement("input", {
    type: "password",
    placeholder: "First name"
  }), /*#__PURE__*/_react.default.createElement("input", {
    type: "password",
    placeholder: "Last name"
  }), /*#__PURE__*/_react.default.createElement("div", {
    id: "buttons"
  }, /*#__PURE__*/_react.default.createElement("button", null, "Sign up"), /*#__PURE__*/_react.default.createElement("button", {
    onClick: function onClick() {
      return ToLogIn();
    }
  }, "Log in")));
};