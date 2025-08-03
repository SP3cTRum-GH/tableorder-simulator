import React, { useState, useEffect } from "react";
import AdminHeader from "../../include/AdminHeader";
import {
  createTable,
  getAllTables,
  updateTable,
  deleteTable,
} from "../../api/TableApi";
import { Button, Form, Modal, ListGroup } from "react-bootstrap";

const TableSetting = () => {
  const [tables, setTables] = useState([]);
  const [showAddModal, setShowAddModal] = useState(false);
  const [showEditModal, setShowEditModal] = useState(false);
  const [showDeleteConfirmModal, setShowDeleteConfirmModal] = useState(false);
  const [newTableName, setNewTableName] = useState("");
  const [editingTable, setEditingTable] = useState(null);
  const [tableToDelete, setTableToDelete] = useState(null);

  const fetchTables = async () => {
    const response = await getAllTables();
    setTables(response);
  };

  useEffect(() => {
    fetchTables();
  }, []);

  const handleAddShow = () => setShowAddModal(true);
  const handleAddClose = () => {
    setShowAddModal(false);
    setNewTableName("");
  };

  const handleAddTable = async () => {
    const memberNo = 1;
    await createTable({ tableName: newTableName, memberNo: memberNo });
    handleAddClose();
    fetchTables();
  };

  const handleEditShow = (table) => {
    setEditingTable(table);
    setNewTableName(table.tableName);
    setShowEditModal(true);
  };
  const handleEditClose = () => {
    setShowEditModal(false);
    setEditingTable(null);
    setNewTableName("");
  };

  const handleUpdateTable = async () => {
    await updateTable(editingTable.tableNo, {
      tableName: newTableName,
      memberNo: editingTable.memberNo,
    });
    handleEditClose();
    fetchTables();
  };

  const handleDeleteShowConfirmModal = (tableNo) => {
    setTableToDelete(tableNo);
    setShowDeleteConfirmModal(true);
  };

  const handleDeleteCloseConfirmModal = () => {
    setShowDeleteConfirmModal(false);
    setTableToDelete(null);
  };

  const handleDeleteTable = async () => {
    if (!tableToDelete) return;

    await deleteTable(tableToDelete);
    fetchTables();
    handleDeleteCloseConfirmModal();
  };

  return (
    <>
      <AdminHeader />
      <h1>Table Setting</h1>
      <Button onClick={handleAddShow}>테이블 추가</Button>

      <ListGroup className="mt-3">
        {tables.map((table) => (
          <ListGroup.Item key={table.tableNo}>
            {table.tableName}
            <Button
              variant="warning"
              size="sm"
              className="ms-2"
              onClick={() => handleEditShow(table)}
            >
              수정
            </Button>
            <Button
              variant="danger"
              size="sm"
              className="ms-2"
              onClick={() => handleDeleteShowConfirmModal(table.tableNo)}
            >
              삭제
            </Button>
          </ListGroup.Item>
        ))}
      </ListGroup>

      <Modal show={showAddModal} onHide={handleAddClose}>
        <Modal.Header closeButton>
          <Modal.Title>테이블 추가</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form.Control
            type="text"
            placeholder="테이블 이름을 입력하세요."
            value={newTableName}
            onChange={(e) => setNewTableName(e.target.value)}
          />
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleAddClose}>
            취소
          </Button>
          <Button variant="primary" onClick={handleAddTable}>
            추가
          </Button>
        </Modal.Footer>
      </Modal>

      <Modal show={showEditModal} onHide={handleEditClose}>
        <Modal.Header closeButton>
          <Modal.Title>테이블 수정</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form.Control
            type="text"
            placeholder="테이블 이름을 입력하세요."
            value={newTableName}
            onChange={(e) => setNewTableName(e.target.value)}
          />
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleEditClose}>
            취소
          </Button>
          <Button variant="primary" onClick={handleUpdateTable}>
            수정
          </Button>
        </Modal.Footer>
      </Modal>

      <Modal
        show={showDeleteConfirmModal}
        onHide={handleDeleteCloseConfirmModal}
        centered
      >
        <Modal.Header closeButton>
          <Modal.Title>테이블 삭제 확인</Modal.Title>
        </Modal.Header>
        <Modal.Body>이 테이블을 정말 삭제하시겠습니까?</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleDeleteCloseConfirmModal}>
            취소
          </Button>
          <Button variant="danger" onClick={handleDeleteTable}>
            삭제
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default TableSetting;
