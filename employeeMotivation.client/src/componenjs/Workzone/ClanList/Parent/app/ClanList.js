"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.ClanList = void 0;
var _react = require("react");
var _Clan = require("../../Clan/app/Clan");
require("../styles/ClanListstyles.css");
var _io = require("react-icons/io5");
var _reactModal = _interopRequireDefault(require("react-modal"));
var _MyModal = require("../../../../MyModal/Modal/app/MyModal");
var _Items = require("../../../../../entities/Items.interface");
var _customStyleModal = require("../../../../../helpers/styles/customStyleModal");
var _FilterText = require("../../../../../functions/filterText/FilterText");
function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
function _slicedToArray(arr, i) { return _arrayWithHoles(arr) || _iterableToArrayLimit(arr, i) || _unsupportedIterableToArray(arr, i) || _nonIterableRest(); }
function _nonIterableRest() { throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method."); }
function _unsupportedIterableToArray(o, minLen) { if (!o) return; if (typeof o === "string") return _arrayLikeToArray(o, minLen); var n = Object.prototype.toString.call(o).slice(8, -1); if (n === "Object" && o.constructor) n = o.constructor.name; if (n === "Map" || n === "Set") return Array.from(o); if (n === "Arguments" || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n)) return _arrayLikeToArray(o, minLen); }
function _arrayLikeToArray(arr, len) { if (len == null || len > arr.length) len = arr.length; for (var i = 0, arr2 = new Array(len); i < len; i++) arr2[i] = arr[i]; return arr2; }
function _iterableToArrayLimit(r, l) { var t = null == r ? null : "undefined" != typeof Symbol && r[Symbol.iterator] || r["@@iterator"]; if (null != t) { var e, n, i, u, a = [], f = !0, o = !1; try { if (i = (t = t.call(r)).next, 0 === l) { if (Object(t) !== t) return; f = !1; } else for (; !(f = (e = i.call(t)).done) && (a.push(e.value), a.length !== l); f = !0); } catch (r) { o = !0, n = r; } finally { try { if (!f && null != t.return && (u = t.return(), Object(u) !== u)) return; } finally { if (o) throw n; } } return a; } }
function _arrayWithHoles(arr) { if (Array.isArray(arr)) return arr; }
var ClanList = exports.ClanList = function ClanList() {
  var _useState = (0, _react.useState)({
      children: []
    }),
    _useState2 = _slicedToArray(_useState, 1),
    clans = _useState2[0];

  // useEffect(() =>{
  //     const getAllClans = async() =>{
  //         const accessToken = localStorage.getItem('accessToken')
  //         const query = ""
  //         const response = await axios.request({
  //             url: `http://localhost:8080/api/clan/search/?query=${query}`,
  //             method: 'get',
  //             headers: {
  //                 Authorization: `${accessToken}`,
  //             }
  //         })

  //         console.log(response)
  //     }

  //     getAllClans();
  // })

  var _useState3 = (0, _react.useState)(clans),
    _useState4 = _slicedToArray(_useState3, 2),
    filteredClans = _useState4[0],
    setFilteredClans = _useState4[1];
  var _useState5 = (0, _react.useState)(false),
    _useState6 = _slicedToArray(_useState5, 2),
    modalIsOpen = _useState6[0],
    setModalIsOpen = _useState6[1];
  var handleClickOpenModal = function handleClickOpenModal() {
    setModalIsOpen(true);
  };
  var handleClickCloseModal = function handleClickCloseModal() {
    setModalIsOpen(false);
  };
  var handleChangeSearchClan = function handleChangeSearchClan(event) {
    var searchTerm = event.target.value;
    var filteredData = clans.children.filter(function (item) {
      return item.name.toLowerCase().includes(searchTerm.toLowerCase());
    });
    setFilteredClans({
      children: filteredData
    });
  };
  return /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("div", {
    className: "mainView"
  }, /*#__PURE__*/React.createElement("h1", null, "still in work..."), /*#__PURE__*/React.createElement("div", {
    className: "clanListMainView"
  }, /*#__PURE__*/React.createElement("div", {
    className: "searchConatiner"
  }, /*#__PURE__*/React.createElement("div", {
    className: "searchIcon"
  }, /*#__PURE__*/React.createElement(_io.IoSearchOutline, null)), /*#__PURE__*/React.createElement("div", null, ">"), /*#__PURE__*/React.createElement("input", {
    type: "text",
    onChange: handleChangeSearchClan,
    readOnly: true
  })), /*#__PURE__*/React.createElement("button", {
    onClick: handleClickOpenModal,
    disabled: true
  }, "Add clan"), /*#__PURE__*/React.createElement("div", {
    id: "clansContainer"
  }, (0, _FilterText.FilterTextForClan)(filteredClans.children).map(function (item, index) {
    return /*#__PURE__*/React.createElement(_Clan.Clan, {
      id: index,
      key: index,
      name: item.name,
      type: "show_join"
    });
  })))), /*#__PURE__*/React.createElement(_reactModal.default, {
    isOpen: modalIsOpen,
    onRequestClose: handleClickCloseModal,
    style: _customStyleModal.customStyles
  }, /*#__PURE__*/React.createElement(_MyModal.MyModal, {
    type: "createClan",
    closeModal: handleClickCloseModal
  })));
};