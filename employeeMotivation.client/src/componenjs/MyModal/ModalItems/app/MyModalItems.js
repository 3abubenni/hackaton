"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.MyModalItems = void 0;
var _react = require("react");
var _Modal = require("../../../../entities/Modal.interface");
require("../styles/MyModalItemsstyles.css");
var _Items = require("../../../../entities/Items.interface");
function _slicedToArray(arr, i) { return _arrayWithHoles(arr) || _iterableToArrayLimit(arr, i) || _unsupportedIterableToArray(arr, i) || _nonIterableRest(); }
function _nonIterableRest() { throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method."); }
function _unsupportedIterableToArray(o, minLen) { if (!o) return; if (typeof o === "string") return _arrayLikeToArray(o, minLen); var n = Object.prototype.toString.call(o).slice(8, -1); if (n === "Object" && o.constructor) n = o.constructor.name; if (n === "Map" || n === "Set") return Array.from(o); if (n === "Arguments" || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)) return _arrayLikeToArray(o, minLen); }
function _arrayLikeToArray(arr, len) { if (len == null || len > arr.length) len = arr.length; for (var i = 0, arr2 = new Array(len); i < len; i++) arr2[i] = arr[i]; return arr2; }
function _iterableToArrayLimit(r, l) { var t = null == r ? null : "undefined" != typeof Symbol && r[Symbol.iterator] || r["@@iterator"]; if (null != t) { var e, n, i, u, a = [], f = !0, o = !1; try { if (i = (t = t.call(r)).next, 0 === l) { if (Object(t) !== t) return; f = !1; } else for (; !(f = (e = i.call(t)).done) && (a.push(e.value), a.length !== l); f = !0); } catch (r) { o = !0, n = r; } finally { try { if (!f && null != t.return && (u = t.return(), Object(u) !== u)) return; } finally { if (o) throw n; } } return a; } }
function _arrayWithHoles(arr) { if (Array.isArray(arr)) return arr; }
var MyModalItems = exports.MyModalItems = function MyModalItems(_ref) {
  var id = _ref.id,
    name = _ref.name,
    description = _ref.description,
    type = _ref.type,
    img = _ref.img,
    money = _ref.money,
    exp = _ref.exp,
    remove = _ref.remove,
    closeModal = _ref.closeModal;
  var _useState = (0, _react.useState)({
      id: id,
      title: name,
      description: description,
      exp: exp,
      money: money
    }),
    _useState2 = _slicedToArray(_useState, 1),
    task = _useState2[0];
  var RemoveTask = function RemoveTask() {
    remove(task);
    closeModal();
  };
  return /*#__PURE__*/React.createElement("div", {
    className: "myModalItemsView",
    style: type == 'task' ? {
      height: '500px'
    } : {
      height: '750px'
    }
  }, img ? /*#__PURE__*/React.createElement("img", {
    src: img
  }) : "", /*#__PURE__*/React.createElement("div", {
    className: "elements"
  }, /*#__PURE__*/React.createElement("label", {
    htmlFor: ""
  }, /*#__PURE__*/React.createElement("p", null, "Title:"), ">", " ", name), /*#__PURE__*/React.createElement("p", null, "Description:"), /*#__PURE__*/React.createElement("div", {
    className: "answer_description"
  }, "> ", /*#__PURE__*/React.createElement("textarea", {
    name: "",
    id: "",
    readOnly: true
  }, description)), money ? /*#__PURE__*/React.createElement("label", {
    htmlFor: ""
  }, /*#__PURE__*/React.createElement("p", null, "Money:"), ">", "  ", money) : "", exp ? /*#__PURE__*/React.createElement("label", {
    htmlFor: ""
  }, /*#__PURE__*/React.createElement("p", null, "Experience: "), ">", " ", exp) : "", type == 'answer' ? /*#__PURE__*/React.createElement("label", {
    htmlFor: ""
  }, "Put your answer") : "", type == 'answer' ? /*#__PURE__*/React.createElement("input", {
    type: "file"
  }) : "", type == 'answer' ? /*#__PURE__*/React.createElement("div", {
    className: "answer_description"
  }, ">", /*#__PURE__*/React.createElement("textarea", {
    name: "",
    id: "answer_description"
  })) : ""), type == 'task' ? /*#__PURE__*/React.createElement("button", {
    onClick: RemoveTask
  }, "Take task") : type == 'answer' ? /*#__PURE__*/React.createElement("button", {
    onClick: function onClick() {
      return remove(task);
    }
  }, "Send answer") : /*#__PURE__*/React.createElement("button", null, "Buy"));
};