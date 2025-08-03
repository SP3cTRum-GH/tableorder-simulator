import React, { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import AdminHeader from "../../include/AdminHeader";
import { getMenusByCategory, deleteOne } from "../../api/MenuApi";
import { Button, ListGroup, Image } from "react-bootstrap";

export const API_SERVER_HOST = "http://localhost:8080";

const MenuListPage = () => {
  const { categoryId } = useParams();
  const [menus, setMenus] = useState([]);

  const fetchMenus = async () => {
    const response = await getMenusByCategory(categoryId);
    setMenus(response);
  };

  useEffect(() => {
    fetchMenus();
  }, [categoryId]);

  const handleDeleteMenu = async (menuNo) => {
    if (window.confirm("메뉴를 삭제하시겠습니까?")) {
      await deleteOne(menuNo);
      fetchMenus();
    }
  };

  return (
    <>
      <AdminHeader />
      <h1>Menus for Category {categoryId}</h1>
      <Link to={`/admin/menu/${categoryId}/add`}>
        <Button>메뉴 추가</Button>
      </Link>
      <ListGroup className="mt-3">
        {menus.map((menu) => (
          <ListGroup.Item key={menu.menuNo}>
            <div className="d-flex align-items-center">
              {menu.imageUrl && (
                <Image
                  src={`${API_SERVER_HOST}${menu.imageUrl}`}
                  thumbnail
                  style={{ width: "50px", height: "50px", marginRight: "10px" }}
                />
              )}
              {menu.menuName}
              <Button
                variant="danger"
                size="sm"
                className="ms-2"
                onClick={() => handleDeleteMenu(menu.menuNo)}
              >
                삭제
              </Button>
              <Link to={`/admin/menu/${menu.menuNo}/options`}>
                <Button variant="info" size="sm" className="ms-2">
                  옵션 관리
                </Button>
              </Link>
            </div>
          </ListGroup.Item>
        ))}
      </ListGroup>
    </>
  );
};

export default MenuListPage;
