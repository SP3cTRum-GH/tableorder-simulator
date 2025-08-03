import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import AdminHeader from "../../include/AdminHeader";
import { addOptionGroupToMenu } from "../../api/MenuApi";
import {
  getOptionGroupsByMenu,
  createOptionGroup,
  addOptionToGroup,
  deleteOptionGroup,
  deleteOption,
} from "../../api/OptionApi";
import { Button, Form, Modal, ListGroup } from "react-bootstrap";

const OptionManagementPage = () => {
  const { menuId } = useParams();
  const [optionGroups, setOptionGroups] = useState([]);
  const [showGroupModal, setShowGroupModal] = useState(false);
  const [showOptionModal, setShowOptionModal] = useState(false);
  const [newGroupName, setNewGroupName] = useState("");
  const [newIsMultiSelect, setNewIsMultiSelect] = useState(false); // Default to single select (radio)
  const [newOptionName, setNewOptionName] = useState("");
  const [newOptionPrice, setNewOptionPrice] = useState(0);
  const [selectedGroupId, setSelectedGroupId] = useState(null);

  const fetchOptionGroups = async () => {
    const response = await getOptionGroupsByMenu(menuId);
    setOptionGroups(response);
  };

  useEffect(() => {
    fetchOptionGroups();
  }, [menuId]);

  const handleAddOptionGroup = async () => {
    const newGroup = await createOptionGroup({
      groupName: newGroupName,
      isMultiSelect: newIsMultiSelect,
    });
    await addOptionGroupToMenu(menuId, newGroup.groupNo);
    setShowGroupModal(false);
    setNewGroupName("");
    setNewIsMultiSelect(false); // Reset to default
    fetchOptionGroups();
  };

  const handleAddOption = async () => {
    await addOptionToGroup(selectedGroupId, {
      value: newOptionName,
      priceAdd: newOptionPrice,
    });
    setShowOptionModal(false);
    setNewOptionName("");
    setNewOptionPrice(0);
    fetchOptionGroups();
  };

  const handleDeleteOptionGroup = async (groupId) => {
    await deleteOptionGroup(groupId);
    fetchOptionGroups();
  };

  const handleDeleteOption = async (optionId) => {
    await deleteOption(optionId);
    fetchOptionGroups();
  };

  return (
    <>
      <AdminHeader />
      <h1>Option Management for Menu ID: {menuId}</h1>
      <Button onClick={() => setShowGroupModal(true)}>Add Option Group</Button>

      <ListGroup className="mt-3">
        {optionGroups.map((group) => (
          <ListGroup.Item key={group.groupNo}>
            <h5>
              {group.groupName} ({group.isMultiSelect ? "Checkbox" : "Radio"})
              <Button
                variant="danger"
                size="sm"
                className="ms-2"
                onClick={() => handleDeleteOptionGroup(group.groupNo)}
              >
                Delete Group
              </Button>
              <Button
                variant="primary"
                size="sm"
                className="ms-2"
                onClick={() => {
                  setSelectedGroupId(group.groupNo);
                  setShowOptionModal(true);
                }}
              >
                Add Option
              </Button>
            </h5>
            <ListGroup>
              {group.options.map((option) => (
                <ListGroup.Item key={option.optionNo}>
                  {option.value} - {option.priceAdd}원
                  <Button
                    variant="danger"
                    size="sm"
                    className="ms-2"
                    onClick={() => handleDeleteOption(option.optionNo)}
                  >
                    Delete
                  </Button>
                </ListGroup.Item>
              ))}
            </ListGroup>
          </ListGroup.Item>
        ))}
      </ListGroup>

      {/* Add Option Group Modal */}
      <Modal show={showGroupModal} onHide={() => setShowGroupModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Add Option Group</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form.Group className="mb-3">
            <Form.Label>Group Name</Form.Label>
            <Form.Control
              type="text"
              placeholder="Group Name"
              value={newGroupName}
              onChange={(e) => setNewGroupName(e.target.value)}
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Check
              type="checkbox"
              label="Allow Multiple Selections (Checkbox)"
              checked={newIsMultiSelect}
              onChange={(e) => setNewIsMultiSelect(e.target.checked)}
            />
          </Form.Group>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowGroupModal(false)}>
            Close
          </Button>
          <Button variant="primary" onClick={handleAddOptionGroup}>
            Add Group
          </Button>
        </Modal.Footer>
      </Modal>

      {/* Add Option Modal */}
      <Modal show={showOptionModal} onHide={() => setShowOptionModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Add Option</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form.Control
            type="text"
            placeholder="Option Name"
            value={newOptionName}
            onChange={(e) => setNewOptionName(e.target.value)}
            className="mb-2"
          />
          <Form.Control
            type="text"
            placeholder="Price"
            value={newOptionPrice}
            onChange={(e) => setNewOptionPrice(e.target.value)}
          />
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowOptionModal(false)}>
            Close
          </Button>
          <Button variant="primary" onClick={handleAddOption}>
            Add Option
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default OptionManagementPage;
