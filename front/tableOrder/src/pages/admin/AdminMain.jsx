import React, { useState, useEffect } from "react";
import AdminHeader from "../../include/AdminHeader";
import { getAllTables } from "../../api/TableApi";
import { getCartByTableNo, removeItemFromCart } from "../../api/CartApi";
import {
  Card,
  Row,
  Col,
  Container,
  Button,
  ListGroup,
  Modal,
} from "react-bootstrap";

export const API_SERVER_HOST = "http://localhost:8080";

const AdminMain = () => {
  const [tables, setTables] = useState([]);
  const [showConfirmModal, setShowConfirmModal] = useState(false);
  const [orderToDelete, setOrderToDelete] = useState(null);

  const fetchTablesAndOrders = async () => {
    const tablesResponse = await getAllTables();
    const tablesWithOrders = await Promise.all(
      tablesResponse.map(async (table) => {
        const cart = await getCartByTableNo(table.tableNo);
        return { ...table, orders: cart.orders };
      })
    );
    setTables(tablesWithOrders);
  };

  useEffect(() => {
    fetchTablesAndOrders();
  }, []);

  const handleShowConfirmModal = (orderNo, tableNo) => {
    setOrderToDelete({ orderNo, tableNo });
    setShowConfirmModal(true);
  };

  const handleCloseConfirmModal = () => {
    setShowConfirmModal(false);
    setOrderToDelete(null);
  };

  const handleConfirmDelete = async () => {
    if (!orderToDelete) return;

    await removeItemFromCart(orderToDelete.orderNo);
    const updatedCart = await getCartByTableNo(orderToDelete.tableNo);
    setTables((prevTables) =>
      prevTables.map((table) =>
        table.tableNo === orderToDelete.tableNo
          ? { ...table, orders: updatedCart.orders }
          : table
      )
    );
    alert("Order marked as complete and removed from cart.");
    handleCloseConfirmModal();
  };

  return (
    <>
      <AdminHeader />
      <Container className="mt-3">
        <h1>Admin Main Page</h1>
        <h2>Tables</h2>
        <Row xs={1} md={2} lg={3} className="g-4">
          {tables.map((table) => (
            <Col key={table.tableNo}>
              <Card>
                <Card.Body>
                  <Card.Title>{table.tableName}</Card.Title>
                  <Card.Text>Table No: {table.tableNo}</Card.Text>
                  <div className="mt-3">
                    {table.orders && table.orders.length > 0 ? (
                      <ListGroup variant="flush">
                        {table.orders.map((order) => (
                          <ListGroup.Item key={order.orderNo}>
                            <div className="d-flex justify-content-between align-items-center">
                              <div>
                                <strong>{order.menuName}</strong> -{" "}
                                {order.quantity}개 (
                                {order.menuPrice * order.quantity}원)
                              </div>
                              <Button
                                variant="success"
                                size="sm"
                                onClick={() =>
                                  handleShowConfirmModal(
                                    order.orderNo,
                                    table.tableNo
                                  )
                                }
                              >
                                조리완료
                              </Button>
                            </div>
                            {order.orderOptions &&
                              order.orderOptions.length > 0 && (
                                <small>
                                  옵션:{" "}
                                  {order.orderOptions
                                    .map(
                                      (opt) =>
                                        `${opt.optionValue}(+${opt.optionPrice}원)`
                                    )
                                    .join(", ")}
                                </small>
                              )}
                          </ListGroup.Item>
                        ))}
                      </ListGroup>
                    ) : (
                      <p>아직 주문이 없습니다.</p>
                    )}
                  </div>
                </Card.Body>
              </Card>
            </Col>
          ))}
        </Row>
      </Container>

      <Modal show={showConfirmModal} onHide={handleCloseConfirmModal} centered>
        <Modal.Header closeButton>
          <Modal.Title>주문완료처리</Modal.Title>
        </Modal.Header>
        <Modal.Body>주문을 완료처리하시겠습니까?</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseConfirmModal}>
            아니오
          </Button>
          <Button variant="success" onClick={handleConfirmDelete}>
            예
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default AdminMain;
