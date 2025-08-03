import axios from "axios";

export const API_SERVER_HOST = "http://localhost:8080";
const prefix = `${API_SERVER_HOST}/api/cart`;

export const addItemToCart = async (cartItemDTO) => {
  const res = await axios.post(`${prefix}/add`, cartItemDTO);
  return res.data;
};

export const getCartByTableNo = async (tableNo) => {
  const res = await axios.get(`${prefix}/${tableNo}`);
  return res.data;
};

export const removeItemFromCart = async (orderNo) => {
  const res = await axios.delete(`${prefix}/remove/${orderNo}`);
  return res.data;
};
