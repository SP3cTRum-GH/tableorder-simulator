import React, { Suspense, lazy } from "react";
import { createBrowserRouter } from "react-router-dom";
import Loading from "../pages/Loading";

const AdminMain = lazy(() => import("../pages/admin/AdminMain"));
const ShopSetting = lazy(() => import("../pages/admin/ShopSetting"));
const MenuSetting = lazy(() => import("../pages/admin/MenuSetting"));
const TableSetting = lazy(() => import("../pages/admin/TableSetting"));
const MenuListPage = lazy(() => import("../pages/admin/MenuListPage"));
const AddMenuPage = lazy(() => import("../pages/admin/AddMenuPage"));
const OptionManagementPage = lazy(() => import("../pages/admin/OptionManagementPage"));

const MainPage = lazy(() => import("../pages/MainPage"));
const CustomerPage = lazy(() => import("../pages/customer/CustomerPage"));
const CustomerMenuPage = lazy(() => import("../pages/customer/CustomerMenuPage"));

const root = createBrowserRouter([
  {
    path: "/",
    element: (
      <Suspense fallback={<Loading />}>
        <MainPage />
      </Suspense>
    ),
  },
  {
    path: "/customer",
    element: (
      <Suspense fallback={<Loading />}>
        <CustomerPage />
      </Suspense>
    ),
  },
  {
    path: "/customer/menu/:tableNo/:categoryId",
    element: (
      <Suspense fallback={<Loading />}>
        <CustomerMenuPage />
      </Suspense>
    ),
  },
  {
    path: "/admin",
    element: (
      <Suspense fallback={<Loading />}>
        <AdminMain />
      </Suspense>
    ),
  },
  {
    path: "/admin/shop",
    element: (
      <Suspense fallback={<Loading />}>
        <ShopSetting />
      </Suspense>
    ),
  },
  {
    path: "/admin/menu",
    element: (
      <Suspense fallback={<Loading />}>
        <MenuSetting />
      </Suspense>
    ),
  },
  {
    path: "/admin/table",
    element: (
      <Suspense fallback={<Loading />}>
        <TableSetting />
      </Suspense>
    ),
  },
  {
    path: "/admin/menu/:categoryId",
    element: (
      <Suspense fallback={<Loading />}>
        <MenuListPage />
      </Suspense>
    ),
  },
  {
    path: "/admin/menu/:categoryId/add",
    element: (
      <Suspense fallback={<Loading />}>
        <AddMenuPage />
      </Suspense>
    ),
  },
  {
    path: "/admin/menu/:menuId/options",
    element: (
      <Suspense fallback={<Loading />}>
        <OptionManagementPage />
      </Suspense>
    ),
  },
]);
export default root;
