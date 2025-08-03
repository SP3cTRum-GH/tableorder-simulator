import React, { useState, useEffect } from "react";
import CustomerHeader from "../../include/CustomerHeader";
import { Container } from "react-bootstrap";
import { getShopSetting } from "../../api/ShopSettingApi";
import { useLocation } from "react-router-dom";

export const API_SERVER_HOST = "http://localhost:8080";

const CustomerPage = () => {
  const [shopImageUrl, setShopImageUrl] = useState("");
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const tableNo = queryParams.get("tableNo");

  useEffect(() => {
    const fetchShopSetting = async () => {
      const response = await getShopSetting();
      if (response && response.shopImageUrl) {
        setShopImageUrl(`${API_SERVER_HOST}${response.shopImageUrl}`);
      }
    };

    fetchShopSetting();
  }, []);

  return (
    <Container>
      <CustomerHeader tableNo={tableNo} />
      <h1>(Table: {tableNo || "Not Selected"})</h1>
      {shopImageUrl ? (
        <div>
          <img src={shopImageUrl} alt="Shop" style={{ maxWidth: "100%" }} />
        </div>
      ) : (
        <></>
      )}
    </Container>
  );
};

export default CustomerPage;
