"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.MyModal = void 0;
require("../styles/MyModalstyles.css");
var MyModal = exports.MyModal = function MyModal() {
  return /*#__PURE__*/React.createElement("div", {
    className: "myModalView"
  }, /*#__PURE__*/React.createElement("div", {
    className: "inputClanName"
  }, /*#__PURE__*/React.createElement("label", {
    htmlFor: ""
  }, "Enter clan name"), /*#__PURE__*/React.createElement("div", {
    className: "inputValue"
  }, ">", /*#__PURE__*/React.createElement("input", {
    type: "text"
  }))), /*#__PURE__*/React.createElement("div", {
    className: "inputImage"
  }, /*#__PURE__*/React.createElement("label", {
    htmlFor: ""
  }, "Choose image"), /*#__PURE__*/React.createElement("input", {
    type: "file",
    placeholder: "Choose image"
  })), /*#__PURE__*/React.createElement("button", null, "Create Clan"));
};