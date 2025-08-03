import axios from "axios";

export const API_SERVER_HOST = "http://localhost:8080";
const prefix = `${API_SERVER_HOST}/api/tables`;

export const createTable = async (tableDTO) => {
  const res = await axios.post(`${prefix}`, tableDTO);
  return res.data;
};

export const getAllTables = async () => {
  const res = await axios.get(`${prefix}`);
  return res.data;
};

export const updateTable = async (tableNo, tableDTO) => {
  const res = await axios.put(`${prefix}/${tableNo}`, tableDTO);
  return res.data;
};

export const deleteTable = async (tableNo) => {
  const res = await axios.delete(`${prefix}/${tableNo}`);
  return res.data;
};
