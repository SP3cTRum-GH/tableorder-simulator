import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import CustomerHeader from "../../include/CustomerHeader";
import { getMenusByCategory } from "../../api/MenuApi";
import { addItemToCart } from "../../api/CartApi";
import {
  Container,
  Card,
  Row,
  Col,
  Modal,
  Button,
  ListGroup,
  Form,
} from "react-bootstrap";

export const API_SERVER_HOST = "http://localhost:8080";

const CustomerMenuPage = () => {
  const { tableNo, categoryId } = useParams();
  const [menus, setMenus] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [selectedMenu, setSelectedMenu] = useState(null);
  const [selectedOptions, setSelectedOptions] = useState({});
  const [quantity, setQuantity] = useState(1);

  useEffect(() => {
    const fetchMenus = async () => {
      const response = await getMenusByCategory(categoryId);
      setMenus(response);
    };

    fetchMenus();
  }, [categoryId]);

  const handleMenuClick = (menu) => {
    setSelectedMenu(menu);
    const initialSelectedOptions = {};
    menu.optionGroups.forEach((group) => {
      if (!group.isMultiSelect) {
        initialSelectedOptions[group.groupNo] = null;
      } else {
        initialSelectedOptions[group.groupNo] = [];
      }
    });
    setSelectedOptions(initialSelectedOptions);
    setQuantity(1);
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setSelectedMenu(null);
    setSelectedOptions({});
    setQuantity(1);
  };

  const handleOptionChange = (groupNo, optionNo, isMultiSelect) => {
    setSelectedOptions((prevSelectedOptions) => {
      const newSelectedOptions = { ...prevSelectedOptions };
      if (!isMultiSelect) {
        newSelectedOptions[groupNo] = optionNo;
      } else {
        const currentSelections = newSelectedOptions[groupNo] || [];
        if (currentSelections.includes(optionNo)) {
          newSelectedOptions[groupNo] = currentSelections.filter(
            (id) => id !== optionNo
          );
        } else {
          newSelectedOptions[groupNo] = [...currentSelections, optionNo];
        }
      }
      return newSelectedOptions;
    });
  };

  const handleAddToCart = async () => {
    if (!selectedMenu) return;

    const parsedTableNo = parseInt(tableNo);
    if (isNaN(parsedTableNo)) {
      alert("테이블번호를 확인해주세요.");
      return;
    }

    const selectedOptionNos = [];
    for (const groupNo in selectedOptions) {
      const selected = selectedOptions[groupNo];
      if (selected) {
        if (Array.isArray(selected)) {
          selectedOptionNos.push(...selected);
        } else {
          selectedOptionNos.push(selected);
        }
      }
    }

    const cartItem = {
      tableNo: parsedTableNo,
      menuNo: selectedMenu.menuNo,
      quantity: quantity,
      selectedOptionNos: selectedOptionNos,
    };

    await addItemToCart(cartItem);
    alert("장바구니 담기 성공");
    handleCloseModal();
  };

  return (
    <Container fluid>
      <CustomerHeader tableNo={tableNo} />
      <Row xs={8} sm={8} md={4} lg={4} className="g-4 justify-content-left">
        {menus.map((menu) => (
          <Col key={menu.menuNo} className="d-flex justify-content-left">
            <Card
              onClick={() => handleMenuClick(menu)}
              style={{ width: "600px", cursor: "pointer" }}
            >
              {/* Fixed card size */}
              {menu.imageUrl && (
                <Card.Img
                  variant="top"
                  src={`${API_SERVER_HOST}${menu.imageUrl}`}
                  style={{ height: "200px", objectFit: "fill" }}
                />
              )}
              <Card.Body>
                <Card.Title>{menu.menuName}</Card.Title>
                <Card.Text>Price: {menu.price}원</Card.Text>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>

      {/* Menu Options Modal */}
      <Modal show={showModal} onHide={handleCloseModal} centered>
        <Modal.Header closeButton>
          <Modal.Title>{selectedMenu?.menuName} Options</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {selectedMenu &&
          selectedMenu.optionGroups &&
          selectedMenu.optionGroups.length > 0 ? (
            <Form>
              {selectedMenu.optionGroups.map((group) => (
                <div key={group.groupNo} className="mb-3">
                  <h5>{group.groupName}</h5>
                  {!group.isMultiSelect
                    ? group.options.map((option) => (
                        <Form.Check
                          key={option.optionNo}
                          type="radio"
                          id={`radio-${group.groupNo}-${option.optionNo}`}
                          label={`${option.value} (+${option.priceAdd}원)`}
                          name={`optionGroup-${group.groupNo}`}
                          checked={
                            selectedOptions[group.groupNo] === option.optionNo
                          }
                          onChange={() =>
                            handleOptionChange(
                              group.groupNo,
                              option.optionNo,
                              group.isMultiSelect
                            )
                          }
                        />
                      ))
                    : group.options.map((option) => (
                        <Form.Check
                          key={option.optionNo}
                          type="checkbox"
                          id={`checkbox-${group.groupNo}-${option.optionNo}`}
                          label={`${option.value} (+${option.priceAdd}원)`}
                          checked={
                            selectedOptions[group.groupNo]?.includes(
                              option.optionNo
                            ) || false
                          }
                          onChange={() =>
                            handleOptionChange(
                              group.groupNo,
                              option.optionNo,
                              group.isMultiSelect
                            )
                          }
                        />
                      ))}
                </div>
              ))}
            </Form>
          ) : (
            <p>No options available for this menu.</p>
          )}
          <Form.Group className="mb-3">
            <Form.Label>Quantity</Form.Label>
            <Form.Control
              type="number"
              min="1"
              value={quantity}
              onChange={(e) => setQuantity(parseInt(e.target.value))}
            />
          </Form.Group>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="primary" onClick={handleAddToCart}>
            Add to Cart
          </Button>
          <Button variant="secondary" onClick={handleCloseModal}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
};

export default CustomerMenuPage;
