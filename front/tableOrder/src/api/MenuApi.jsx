import axios from "axios";

export const API_SERVER_HOST = "http://localhost:8080";
const prefix = `${API_SERVER_HOST}/api/menu`;

export const addOptionGroupToMenu = async (menuNo, groupNo) => {
  const res = await axios.post(`${prefix}/${menuNo}/option-groups/${groupNo}`);
  return res.data;
};

export const getList = async (pageParam) => {
  const { page, size } = pageParam;
  const res = await axios.get(`${prefix}/list`, {
    params: { page: page, size: size },
  });
  return res.data;
};

export const getMenusByCategory = async (categoryNo) => {
  const res = await axios.get(`${prefix}/category/${categoryNo}`);
  return res.data;
};

export const createMenu = async (menuDTO) => {
  const res = await axios.post(`${prefix}`, menuDTO);
  return res.data;
};

export const postAdd = async (product) => {
  const header = { headers: { "Content-Type": "multipart/form-data" } };
  const result = await axios.post(`${prefix}/`, product, header);
  return result.data;
};

export const deleteOne = async (tno) => {
  const res = await axios.delete(`${prefix}/${tno}`);
  return res.data;
};

export const putOne = async (todo) => {
  const res = await axios.put(`${prefix}/${todo.tno}`, todo);
  return res.data;
};
