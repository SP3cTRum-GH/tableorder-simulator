import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import AdminHeader from "../../include/AdminHeader";
import {
  getCategoriesByMember,
  createCategory,
  deleteCategory,
} from "../../api/CategoryApi";
import { Button, Form, Modal, ListGroup } from "react-bootstrap";

const MenuSetting = () => {
  const [categories, setCategories] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [newCategoryName, setNewCategoryName] = useState("");

  const fetchCategories = async () => {
    const memberNo = 1;
    const response = await getCategoriesByMember(memberNo);
    setCategories(response);
  };

  useEffect(() => {
    fetchCategories();
  }, []);

  const handleShow = () => setShowModal(true);
  const handleClose = () => {
    setShowModal(false);
    setNewCategoryName("");
  };

  const handleAddCategory = async () => {
    const memberNo = 1;
    await createCategory({ name: newCategoryName, memberNo: memberNo });
    handleClose();
    fetchCategories();
  };

  const handleDeleteCategory = async (categoryNo) => {
    if (window.confirm("카테고리를 삭제하시겠습니까?")) {
      await deleteCategory(categoryNo);
      fetchCategories();
    }
  };

  return (
    <>
      <AdminHeader />
      <h1>Menu Setting</h1>
      <div>
        <h2>Categories</h2>
        <ListGroup>
          {categories.map((category) => (
            <ListGroup.Item key={category.categoryNo}>
              <Link to={`/admin/menu/${category.categoryNo}`}>
                {category.name}
              </Link>
              <Button
                variant="danger"
                size="sm"
                className="ms-2"
                onClick={() => handleDeleteCategory(category.categoryNo)}
              >
                Delete
              </Button>
            </ListGroup.Item>
          ))}
        </ListGroup>
      </div>
      <Button className="mt-3" onClick={handleShow}>
        카테고리 추가
      </Button>

      <Modal show={showModal} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>카테고리 추가</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form.Control
            type="text"
            placeholder="카테고리 이름을 입력하세요."
            value={newCategoryName}
            onChange={(e) => setNewCategoryName(e.target.value)}
          />
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            취소
          </Button>
          <Button variant="primary" onClick={handleAddCategory}>
            추가
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default MenuSetting;
