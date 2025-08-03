import axios from "axios";

export const API_SERVER_HOST = "http://localhost:8080";
const prefix = `${API_SERVER_HOST}/api/shop-setting`;

export const saveShopSetting = async (shopSettingDTO) => {
  const res = await axios.post(`${prefix}`, shopSettingDTO);
  return res.data;
};

export const getShopSetting = async () => {
  const res = await axios.get(`${prefix}`);
  return res.data;
};
