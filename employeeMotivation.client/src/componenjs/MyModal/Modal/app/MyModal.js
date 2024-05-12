"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.MyModal = void 0;
var _react = require("react");
require("../styles/MyModalstyles.css");
var _ModalBodyTask = _interopRequireDefault(require("../../../ModalBody/ModalBodyTask"));
var _ModalBodyClan = _interopRequireDefault(require("../../../ModalBody/ModalBodyClan"));
var _ModalBodyStroe = _interopRequireDefault(require("../../../ModalBody/ModalBodyStroe"));
function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
var MyModal = exports.MyModal = function MyModal(_ref) {
  var type = _ref.type,
    closeModal = _ref.closeModal;
  return /*#__PURE__*/React.createElement("div", {
    className: "myModalView",
    style: type === 'addTask' ? {
      height: '600px',
      width: '600px'
    } : type === 'store' ? {
      height: '850px',
      width: '700px'
    } : {}
  }, /*#__PURE__*/React.createElement("div", {
    className: "input"
  }, type == 'addTask' ? /*#__PURE__*/React.createElement(_ModalBodyTask.default, {
    closeModal: closeModal
  }) : type == 'createClan' ? /*#__PURE__*/React.createElement(_ModalBodyClan.default, {
    closeModal: closeModal
  }) : /*#__PURE__*/React.createElement(_ModalBodyStroe.default, {
    closeModal: closeModal
  })));
};