import React, { useState, useEffect } from "react";
import { Container, Button, Modal, ListGroup } from "react-bootstrap";
import Header from "../include/Header";
import { useNavigate } from "react-router-dom";
import { getAllTables } from "../api/TableApi";

function MainPage() {
  const navigate = useNavigate();
  const [showTableModal, setShowTableModal] = useState(false);
  const [tables, setTables] = useState([]);
  const [selectedTable, setSelectedTable] = useState(null);

  useEffect(() => {
    const fetchTables = async () => {
      try {
        const response = await getAllTables();
        setTables(response);
      } catch (error) {
        console.error("Error fetching tables:", error);
      }
    };

    fetchTables();
  }, []);

  const handleShowTableModal = () => setShowTableModal(true);
  const handleCloseTableModal = () => setShowTableModal(false);

  const handleTableSelect = (table) => {
    setSelectedTable(table);
    handleCloseTableModal();
    navigate(`/customer?tableNo=${table.tableNo}`);
  };

  return (
    <>
      <Container>
        <Header />
        <div className="d-grid gap-2 mt-3">
          <button
            className="btn btn-outline-secondary"
            type="button"
            onClick={() => {
              navigate({ pathname: `/admin` });
            }}
          >
            Admin Page
          </button>

          <button
            className="btn btn-outline-secondary mt-3"
            type="button"
            onClick={handleShowTableModal}
          >
            Customer Page
          </button>
        </div>
      </Container>

      <Modal show={showTableModal} onHide={handleCloseTableModal} centered>
        <Modal.Header closeButton>
          <Modal.Title>테이블을 선택해주세요</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {tables.length > 0 ? (
            <ListGroup>
              {tables.map((table) => (
                <ListGroup.Item
                  key={table.tableNo}
                  action
                  onClick={() => handleTableSelect(table)}
                >
                  {table.tableName}
                </ListGroup.Item>
              ))}
            </ListGroup>
          ) : (
            <></>
          )}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseTableModal}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default MainPage;
