import React, { useState, useEffect } from "react";
import AdminHeader from "../../include/AdminHeader";
import axios from "axios";
import { saveShopSetting, getShopSetting } from "../../api/ShopSettingApi";

export const API_SERVER_HOST = "http://localhost:8080";

const ShopSetting = () => {
  const [selectedFile, setSelectedFile] = useState(null);
  const [imageUrl, setImageUrl] = useState("");
  const [uploadMessage, setUploadMessage] = useState("");

  useEffect(() => {
    const fetchCurrentShopSetting = async () => {
      const response = await getShopSetting();
      if (response && response.shopImageUrl) {
        setImageUrl(response.shopImageUrl);
      }
    };
    fetchCurrentShopSetting();
  }, []);

  const handleFileChange = async (event) => {
    const newFile = event.target.files[0];
    setSelectedFile(newFile);
    setUploadMessage("");

    if (imageUrl) {
      await axios.delete(`${API_SERVER_HOST}/api/files/delete`, {
        params: { filePath: imageUrl },
      });
      console.log("Old image deleted from server:", imageUrl);
    }
    setImageUrl("");
  };

  const handleUpload = async () => {
    if (!selectedFile) {
      setUploadMessage("Please select a file first!");
      return;
    }

    const formData = new FormData();
    formData.append("file", selectedFile);

    const response = await axios.post(
      `${API_SERVER_HOST}/api/files/upload`,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      }
    );
    const uploadedRelativeUrl = response.data;
    setImageUrl(uploadedRelativeUrl);
    setUploadMessage("File uploaded successfully!");
    console.log(
      "Uploaded image URL:",
      `${API_SERVER_HOST}${uploadedRelativeUrl}`
    );

    await saveShopSetting({ shopImageUrl: uploadedRelativeUrl });
  };

  return (
    <>
      <AdminHeader />
      <h1>Shop Setting</h1>
      <div>
        {imageUrl && (
          <div>
            <h3>현재 이미지 :</h3>
            <img
              src={`${API_SERVER_HOST}${imageUrl}`}
              alt="Current Shop"
              style={{ maxWidth: "300px" }}
            />
            <p>URL: {`${API_SERVER_HOST}${imageUrl}`}</p>
          </div>
        )}
        <input type="file" onChange={handleFileChange} />
        <button onClick={handleUpload}>Upload Image</button>
        {uploadMessage && <p>{uploadMessage}</p>}
      </div>
    </>
  );
};

export default ShopSetting;
