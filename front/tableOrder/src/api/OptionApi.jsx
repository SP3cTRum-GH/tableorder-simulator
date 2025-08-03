import axios from "axios";

export const API_SERVER_HOST = "http://localhost:8080";
const prefix = `${API_SERVER_HOST}/api/options`;

export const createOptionGroup = async (optionGroupDTO) => {
  const res = await axios.post(`${prefix}/groups`, optionGroupDTO);
  return res.data;
};

export const addOptionToGroup = async (groupId, optionDTO) => {
  const res = await axios.post(
    `${prefix}/groups/${groupId}/options`,
    optionDTO
  );
  return res.data;
};

export const deleteOptionGroup = async (groupId) => {
  const res = await axios.delete(`${prefix}/groups/${groupId}`);
  return res.data;
};

export const deleteOption = async (optionId) => {
  const res = await axios.delete(`${prefix}/options/${optionId}`);
  return res.data;
};

export const getOptionGroupsByMenu = async (menuNo) => {
  const res = await axios.get(`${prefix}/${menuNo}`);
  return res.data;
};
