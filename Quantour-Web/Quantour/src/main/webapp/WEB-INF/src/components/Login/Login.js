/**
 * Created by Hitigerzzz on 2017/5/23.
 */
import React from 'react';
import { Form, Icon, Input, Button, Checkbox } from 'antd';
import QueueAnim from 'rc-queue-anim';
import { connect } from 'dva';
import { Link } from 'dva/router';
import styles from './Login.less';

const FormItem = Form.Item;

class LoginForm extends React.Component {
  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        const data = JSON.parse(JSON.stringify(values));
        const user = {
          username: data.username,
          password: data.password,
        };
        this.props.dispatch({
          type: 'users/signin',
          payload: user,
        });
      }
    });
  };

  render() {
    const { getFieldDecorator } = this.props.form;
    return (
      <Form onSubmit={this.handleSubmit} className={styles.login_form}>
        <QueueAnim
          duration={1000}
        >
          <FormItem key="anim1">
            {getFieldDecorator('username', {
              rules: [{ required: true, message: '请输入用户名！' }],
            })(
              <Input prefix={<Icon type="user" style={{ fontSize: 13 }} />} placeholder="用户名" />,
            )}
          </FormItem>
          <FormItem key="anim2">
            {getFieldDecorator('password', {
              rules: [{ required: true, message: '请输入密码！' }],
            })(
              <Input prefix={<Icon type="lock" style={{ fontSize: 13 }} />} type="password" placeholder="密码" />,
            )}
          </FormItem>
          <FormItem key="anim3">
            {/* {getFieldDecorator('remember', {*/}
            {/* valuePropName: 'checked',*/}
            {/* initialValue: true,*/}
            {/* })(*/}
            {/* <Checkbox>记住密码</Checkbox>,*/}
            {/* )}*/}
            <Link className={styles.login_form_link} to="/forget">忘记密码</Link>
            <Button type="primary" htmlType="submit" className={styles.login_form_button}>
              登录
            </Button>
            <Link className={styles.login_form_link} to="/register">没有账号？现在去注册！</Link>
            <div
              style={{ clear: 'both',
                color: '#e84545',
                textAlign: 'center',
                display: `${this.props.isTrue}` }}
            >用户名或密码错误</div>
          </FormItem>
        </QueueAnim>
      </Form>
    );
  }
}

const Login = Form.create()(LoginForm);

function mapStateToProps(state) {
  const { isTrue } = state.users;
  return { isTrue };
}

export default connect(mapStateToProps)(Login);
