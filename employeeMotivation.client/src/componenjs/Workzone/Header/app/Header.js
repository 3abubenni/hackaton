"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.Header = void 0;
var _ai = require("react-icons/ai");
require("../styles/Headerstyles.css");
var _reactRouterDom = require("react-router-dom");
var _DropDownWindow = require("../../DropDownWindow/app/DropDownWindow");
var _fa = require("react-icons/fa");
var _react = require("react");
function _slicedToArray(arr, i) { return _arrayWithHoles(arr) || _iterableToArrayLimit(arr, i) || _unsupportedIterableToArray(arr, i) || _nonIterableRest(); }
function _nonIterableRest() { throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method."); }
function _unsupportedIterableToArray(o, minLen) { if (!o) return; if (typeof o === "string") return _arrayLikeToArray(o, minLen); var n = Object.prototype.toString.call(o).slice(8, -1); if (n === "Object" && o.constructor) n = o.constructor.name; if (n === "Map" || n === "Set") return Array.from(o); if (n === "Arguments" || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)) return _arrayLikeToArray(o, minLen); }
function _arrayLikeToArray(arr, len) { if (len == null || len > arr.length) len = arr.length; for (var i = 0, arr2 = new Array(len); i < len; i++) arr2[i] = arr[i]; return arr2; }
function _iterableToArrayLimit(r, l) { var t = null == r ? null : "undefined" != typeof Symbol && r[Symbol.iterator] || r["@@iterator"]; if (null != t) { var e, n, i, u, a = [], f = !0, o = !1; try { if (i = (t = t.call(r)).next, 0 === l) { if (Object(t) !== t) return; f = !1; } else for (; !(f = (e = i.call(t)).done) && (a.push(e.value), a.length !== l); f = !0); } catch (r) { o = !0, n = r; } finally { try { if (!f && null != t.return && (u = t.return(), Object(u) !== u)) return; } finally { if (o) throw n; } } return a; } }
function _arrayWithHoles(arr) { if (Array.isArray(arr)) return arr; }
var Header = exports.Header = function Header() {
  var navigate = (0, _reactRouterDom.useNavigate)();
  var _useState = (0, _react.useState)(false),
    _useState2 = _slicedToArray(_useState, 2),
    showWindow = _useState2[0],
    setShowWindow = _useState2[1];
  var handleClickGoToSelections = function handleClickGoToSelections() {
    setShowWindow(!showWindow);
  };
  var handleClickGoToTasks = function handleClickGoToTasks() {
    navigate("tasks");
    setShowWindow(false);
  };
  var handleClickGoToClan = function handleClickGoToClan() {
    navigate("clans");
    setShowWindow(false);
  };
  var handleClickGoToStore = function handleClickGoToStore() {
    navigate("store");
    setShowWindow(false);
  };
  return /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("nav", null, /*#__PURE__*/React.createElement("ul", null, /*#__PURE__*/React.createElement("li", null, /*#__PURE__*/React.createElement("div", {
    id: "iconCloud"
  }, /*#__PURE__*/React.createElement(_ai.AiFillCloud, null))), /*#__PURE__*/React.createElement("li", null, /*#__PURE__*/React.createElement("span", {
    id: "notif"
  }, "2"), /*#__PURE__*/React.createElement("a", {
    href: "#",
    onClick: function onClick() {
      return handleClickGoToTasks();
    }
  }, "Tasks")), /*#__PURE__*/React.createElement("li", null, /*#__PURE__*/React.createElement("a", {
    href: "#",
    onClick: function onClick() {
      return handleClickGoToStore();
    }
  }, "Store")), /*#__PURE__*/React.createElement("li", null, /*#__PURE__*/React.createElement("a", {
    href: "#",
    onClick: function onClick() {
      return handleClickGoToClan();
    }
  }, "Clans")), /*#__PURE__*/React.createElement("li", null, /*#__PURE__*/React.createElement("div", {
    id: "iconUser",
    onClick: function onClick() {
      return handleClickGoToSelections();
    }
  }, /*#__PURE__*/React.createElement(_fa.FaUserCircle, null))))), showWindow ? /*#__PURE__*/React.createElement(_DropDownWindow.DropDownWindow, {
    showDropWindow: setShowWindow
  }) : "");
};