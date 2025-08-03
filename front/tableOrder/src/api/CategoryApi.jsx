import axios from "axios";

export const API_SERVER_HOST = "http://localhost:8080";
const prefix = `${API_SERVER_HOST}/api/categories`;

// Create
export const createCategory = async (categoryDTO) => {
  const res = await axios.post(`${prefix}`, categoryDTO);
  return res.data;
};

// Update
export const updateCategory = async (categoryNo, categoryDTO) => {
  const res = await axios.put(`${prefix}/${categoryNo}`, categoryDTO);
  return res.data;
};

// Delete
export const deleteCategory = async (categoryNo) => {
  const res = await axios.delete(`${prefix}/${categoryNo}`);
  return res.data;
};

// Get all categories
export const getCategoriesByMember = async (memberNo) => {
  const res = await axios.get(`${prefix}/list/${memberNo}`);
  return res.data;
};

// Get item
export const getCategory = async (categoryNo) => {
  const res = await axios.get(`${prefix}/${categoryNo}`);
  return res.data;
};
