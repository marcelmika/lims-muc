/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

import 'rc-tabs/assets/index.css';
import React, { Component } from 'react';
import Tabs, { TabPane } from 'rc-tabs';
import TabContent from 'rc-tabs/lib/TabContent';
import ScrollableInkTabBar from 'rc-tabs/lib/ScrollableInkTabBar';
import './App.css';

class App extends Component {
  render() {
    return (
      <div className="App">
          <Tabs
              defaultActiveKey="1"
              tabBarPosition="top"
              renderTabBar={()=><ScrollableInkTabBar />}
              renderTabContent={()=><TabContent />}
          >
              <TabPane tab='Settings' key="1">Settings</TabPane>
              <TabPane tab='Jabber' key="2">Jabber</TabPane>
              <TabPane tab='Web Sockets' key="3">Web Sockets</TabPane>
              <TabPane tab='Account' key="4">Your Account</TabPane>
          </Tabs>
      </div>
    );
  }
}

export default App;
