import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Container, Nav, Navbar } from "react-bootstrap";
import { Link } from "react-router-dom";

const AdminHeader = () => {
  return (
    <>
      <Navbar
        collapseOnSelect
        bg="primary"
        data-bs-theme="dark"
        className="bg-body-primary"
      >
        <Container>
          <Navbar.Brand as={Link} to="/admin">
            TableOrder Admin
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse id="responsive-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link as={Link} to="/admin/shop">
                매장관리
              </Nav.Link>
              <Nav.Link as={Link} to="/admin/menu">
                메뉴관리
              </Nav.Link>
              <Nav.Link as={Link} to="/admin/table">
                테이블관리
              </Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
    </>
  );
};

export default AdminHeader;
