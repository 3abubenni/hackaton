"use strict";

function _typeof(o) { "@babel/helpers - typeof"; return _typeof = "function" == typeof Symbol && "symbol" == typeof Symbol.iterator ? function (o) { return typeof o; } : function (o) { return o && "function" == typeof Symbol && o.constructor === Symbol && o !== Symbol.prototype ? "symbol" : typeof o; }, _typeof(o); }
Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.Tasks = void 0;
var _io = require("react-icons/io5");
require("../styles/Tasks.css");
var _MyModal = require("../../../../MyModal/Modal/app/MyModal");
var _reactModal = _interopRequireDefault(require("react-modal"));
var _react = require("react");
var _Items = require("../../../../../entities/Items.interface");
var _TaskItem = _interopRequireDefault(require("../../TaskItem/app/TaskItem"));
function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
function ownKeys(e, r) { var t = Object.keys(e); if (Object.getOwnPropertySymbols) { var o = Object.getOwnPropertySymbols(e); r && (o = o.filter(function (r) { return Object.getOwnPropertyDescriptor(e, r).enumerable; })), t.push.apply(t, o); } return t; }
function _objectSpread(e) { for (var r = 1; r < arguments.length; r++) { var t = null != arguments[r] ? arguments[r] : {}; r % 2 ? ownKeys(Object(t), !0).forEach(function (r) { _defineProperty(e, r, t[r]); }) : Object.getOwnPropertyDescriptors ? Object.defineProperties(e, Object.getOwnPropertyDescriptors(t)) : ownKeys(Object(t)).forEach(function (r) { Object.defineProperty(e, r, Object.getOwnPropertyDescriptor(t, r)); }); } return e; }
function _defineProperty(obj, key, value) { key = _toPropertyKey(key); if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }
function _toPropertyKey(t) { var i = _toPrimitive(t, "string"); return "symbol" == _typeof(i) ? i : i + ""; }
function _toPrimitive(t, r) { if ("object" != _typeof(t) || !t) return t; var e = t[Symbol.toPrimitive]; if (void 0 !== e) { var i = e.call(t, r || "default"); if ("object" != _typeof(i)) return i; throw new TypeError("@@toPrimitive must return a primitive value."); } return ("string" === r ? String : Number)(t); }
function _slicedToArray(arr, i) { return _arrayWithHoles(arr) || _iterableToArrayLimit(arr, i) || _unsupportedIterableToArray(arr, i) || _nonIterableRest(); }
function _nonIterableRest() { throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method."); }
function _unsupportedIterableToArray(o, minLen) { if (!o) return; if (typeof o === "string") return _arrayLikeToArray(o, minLen); var n = Object.prototype.toString.call(o).slice(8, -1); if (n === "Object" && o.constructor) n = o.constructor.name; if (n === "Map" || n === "Set") return Array.from(o); if (n === "Arguments" || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)) return _arrayLikeToArray(o, minLen); }
function _arrayLikeToArray(arr, len) { if (len == null || len > arr.length) len = arr.length; for (var i = 0, arr2 = new Array(len); i < len; i++) arr2[i] = arr[i]; return arr2; }
function _iterableToArrayLimit(r, l) { var t = null == r ? null : "undefined" != typeof Symbol && r[Symbol.iterator] || r["@@iterator"]; if (null != t) { var e, n, i, u, a = [], f = !0, o = !1; try { if (i = (t = t.call(r)).next, 0 === l) { if (Object(t) !== t) return; f = !1; } else for (; !(f = (e = i.call(t)).done) && (a.push(e.value), a.length !== l); f = !0); } catch (r) { o = !0, n = r; } finally { try { if (!f && null != t.return && (u = t.return(), Object(u) !== u)) return; } finally { if (o) throw n; } } return a; } }
function _arrayWithHoles(arr) { if (Array.isArray(arr)) return arr; }
var Tasks = exports.Tasks = function Tasks() {
  var _useState = (0, _react.useState)(false),
    _useState2 = _slicedToArray(_useState, 2),
    modalIsOpen = _useState2[0],
    setModalIsOpen = _useState2[1];
  var _useState3 = (0, _react.useState)({
      children: [{
        id: 0,
        title: "Do rtpoop poefwpoewkfffhfffqwfq",
        description: 'You must do RTPO',
        exp: 10,
        money: 100
      }, {
        id: 1,
        title: "Do CHMfffff",
        description: 'You must do CHM',
        exp: 10,
        money: 100
      }, {
        id: 2,
        title: "Do TPR",
        description: 'You must do TPR',
        exp: 10,
        money: 100
      }]
    }),
    _useState4 = _slicedToArray(_useState3, 2),
    tasksList = _useState4[0],
    setTasksList = _useState4[1];
  var FilterText = function FilterText(filteredData) {
    return filteredData.map(function (item) {
      if (item.title.length > 24) {
        return {
          title: item.title.slice(0, 22) + "...",
          description: item.description,
          exp: item.exp,
          money: item.money
        };
      } else {
        return item;
      }
    });
  };
  var _useState5 = (0, _react.useState)(tasksList),
    _useState6 = _slicedToArray(_useState5, 2),
    filteredTasks = _useState6[0],
    setFilteredTasks = _useState6[1];
  var openModal = function openModal() {
    setModalIsOpen(true);
  };
  var closeModal = function closeModal() {
    setModalIsOpen(false);
  };
  var SearchTaskItem = function SearchTaskItem(event) {
    var searchTerm = event.target.value;
    var filteredData = tasksList.children.filter(function (item) {
      return item.title.toLowerCase().includes(searchTerm.toLowerCase());
    });
    setFilteredTasks({
      children: filteredData
    });
  };
  var RemoveTaskItem = function RemoveTaskItem(task) {
    var afterRemoveData = filteredTasks.children.filter(function (task_inList) {
      return task_inList.id !== task.id;
    });
    var updatedAfterRemoveData = afterRemoveData.map(function (task, index) {
      return _objectSpread(_objectSpread({}, task), {}, {
        id: index
      });
    });
    setTasksList({
      children: updatedAfterRemoveData
    });
    setFilteredTasks({
      children: updatedAfterRemoveData
    });
  };
  var customStyles = {
    content: {
      top: '50%',
      left: '50%',
      right: 'auto',
      bottom: 'auto',
      marginRight: '-50%',
      transform: 'translate(-50%, -50%)',
      backgroundColor: 'rgb(22, 62, 73)',
      borderRadius: '20px'
    },
    overlay: {
      backgroundColor: 'rgba(0, 0, 0, 0.5)',
      transition: 'background-color 0.5s ease'
    }
  };
  return /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("div", {
    className: "mainView"
  }, /*#__PURE__*/React.createElement("div", {
    className: "tasksListMainView"
  }, /*#__PURE__*/React.createElement("div", {
    className: "searchConatiner"
  }, /*#__PURE__*/React.createElement("div", {
    className: "searchIcon"
  }, /*#__PURE__*/React.createElement(_io.IoSearchOutline, null)), /*#__PURE__*/React.createElement("div", null, ">"), /*#__PURE__*/React.createElement("input", {
    type: "text",
    onChange: SearchTaskItem
  })), /*#__PURE__*/React.createElement("button", {
    onClick: openModal
  }, "Add store item"), /*#__PURE__*/React.createElement("div", {
    id: "tasksContainer"
  }, FilterText(filteredTasks.children).map(function (item, index) {
    return /*#__PURE__*/React.createElement(_TaskItem.default, {
      key: index,
      id: index,
      title: item.title,
      description: item.description,
      exp: item.exp,
      money: item.money,
      remove: RemoveTaskItem
    });
  })))), /*#__PURE__*/React.createElement(_reactModal.default, {
    isOpen: modalIsOpen,
    onRequestClose: closeModal,
    style: customStyles
  }, /*#__PURE__*/React.createElement(_MyModal.MyModal, {
    inputValue: "Enter name of product"
  })));
};