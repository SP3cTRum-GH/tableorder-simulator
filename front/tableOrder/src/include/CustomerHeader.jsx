import React, { useState, useEffect } from "react";
import { Container, Nav, Navbar } from "react-bootstrap";
import { getCategoriesByMember } from "../api/CategoryApi";
import { Link } from "react-router-dom";

export default function CustomerHeader({ tableNo }) {
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const memberNo = 1;
        const response = await getCategoriesByMember(memberNo);
        setCategories(response);
      } catch (error) {
        console.error("Error fetching categories:", error);
      }
    };

    fetchCategories();
  }, []);

  return (
    <Navbar
      collapseOnSelect
      bg="primary"
      data-bs-theme="dark"
      className="bg-body-primary"
    >
      <Container>
        <Navbar.Brand as={Link} to={`/customer?tableNo=${tableNo}`}>
          TableOrder
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="me-auto">
            {categories.map((category) => (
              <Nav.Link
                key={category.categoryNo}
                as={Link}
                to={`/customer/menu/${tableNo}/${category.categoryNo}`}
              >
                {category.name}
              </Nav.Link>
            ))}
          </Nav>
          <Nav>
            <Nav.Link href="#deets">Login</Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}
