import React, { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import AdminHeader from "../../include/AdminHeader";
import { createMenu } from "../../api/MenuApi";
import { Button, Form } from "react-bootstrap";
import axios from "axios";

export const API_SERVER_HOST = "http://localhost:8080";

const AddMenuPage = () => {
  const { categoryId } = useParams();
  const navigate = useNavigate();
  const [menu, setMenu] = useState({
    menuName: "",
    price: "",
    imageUrl: "",
    available: true,
    soldOut: false,
    categoryNo: categoryId,
  });
  const [selectedFile, setSelectedFile] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setMenu((prevMenu) => ({
      ...prevMenu,
      [name]: value,
    }));
  };

  const handleFileChange = (event) => {
    setSelectedFile(event.target.files[0]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    let uploadedImageUrl = menu.imageUrl;

    if (selectedFile) {
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
      uploadedImageUrl = response.data;
    }

    await createMenu({ ...menu, imageUrl: uploadedImageUrl });
    navigate(`/admin/menu/${categoryId}`);
  };

  return (
    <>
      <AdminHeader />
      <h1>Add Menu to Category {categoryId}</h1>
      <Form onSubmit={handleSubmit}>
        <Form.Group className="mb-3">
          <Form.Label>Menu Name</Form.Label>
          <Form.Control
            type="text"
            name="menuName"
            value={menu.menuName}
            onChange={handleChange}
            required
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Price</Form.Label>
          <Form.Control
            type="number"
            name="price"
            value={menu.price}
            onChange={handleChange}
            required
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>Menu Image</Form.Label>
          <Form.Control type="file" onChange={handleFileChange} />
        </Form.Group>
        <Button variant="primary" type="submit">
          Add Menu
        </Button>
      </Form>
    </>
  );
};

export default AddMenuPage;
