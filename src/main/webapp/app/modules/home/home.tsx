import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';

import { Row, Col, Alert, Button } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

import { getEntities } from 'app/entities/llamado/llamado.reducer';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="9"></Col>
    </Row>
  );
};

export default Home;
