/**
 * Created by wyj on 2017/6/10.
 * description:
 */
import React from 'react';
import { Form, Input, Button } from 'antd';
import QueueAnim from 'rc-queue-anim';
import { connect } from 'dva';
import { Link } from 'dva/router';
import styles from './Forget.less';

const FormItem = Form.Item;

class ForgetForm extends React.Component {
  state = {
    confirmDirty: false,
    autoCompleteResult: [],
  };
  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFieldsAndScroll((err, values) => {
      if (!err) {
        const data = JSON.parse(JSON.stringify(values));
        const user = {
          username: data.username,
          newPassword: data.password,
          phoneNumber: data.phone,
        };
        this.props.dispatch({
          type: 'users/forgetPassword',
          payload: JSON.parse(JSON.stringify(user)),
        });
      }
    });
  };
  handleConfirmBlur = (e) => {
    const value = e.target.value;
    this.setState({ confirmDirty: this.state.confirmDirty || !!value });
  };
  checkPassword = (rule, value, callback) => {
    const form = this.props.form;
    if (value && value !== form.getFieldValue('password')) {
      callback('两次输入的密码不一样！');
    } else {
      callback();
    }
  };
  checkConfirm = (rule, value, callback) => {
    const form = this.props.form;
    if (value && this.state.confirmDirty) {
      form.validateFields(['confirm'], { force: true });
    }
    callback();
  };
  render() {
    const { getFieldDecorator } = this.props.form;
    const formItemLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 },
      },
    };
    const tailFormItemLayout = {
      wrapperCol: {
        xs: {
          span: 24,
          offset: 0,
        },
        sm: {
          span: 14,
          offset: 6,
        },
      },
    };
    return (
      <Form
        onSubmit={this.handleSubmit}
        className={styles.forget_form}
      >
        <QueueAnim
          duration={1000}
        >
          <FormItem
            key="anim1"
            {...formItemLayout}
            label="用户名"
            hasFeedback
          >
            {getFieldDecorator('username', {
              rules: [{ required: true, message: '请输入用户名！' }],
            })(
              <Input />,
            )}
          </FormItem>
          <FormItem
            key="anim2"
            {...formItemLayout}
            label="手机号码"
          >
            {getFieldDecorator('phone', {
              rules: [{ required: true, message: '请输入手机号码！' }],
            })(
              <Input />,
            )}
          </FormItem>
          <FormItem
            key="anim3"
            {...formItemLayout}
            label="新密码"
            hasFeedback
          >
            {getFieldDecorator('password', {
              rules: [{
                required: true, message: '请输入密码！',
              }, {
                validator: this.checkConfirm,
              }],
            })(
              <Input type="password" />,
            )}
          </FormItem>
          <FormItem
            key="anim4"
            {...formItemLayout}
            label="确认新密码"
            hasFeedback
          >
            {getFieldDecorator('confirm', {
              rules: [{
                required: true, message: '请输入密码！',
              }, {
                validator: this.checkPassword,
              }],
            })(
              <Input type="password" onBlur={this.handleConfirmBlur} />,
            )}
          </FormItem>
          <FormItem
            {...tailFormItemLayout}
            key="anim5"
          >
            <Button
              type="primary"
              htmlType="submit"
              size="large"
              className={styles.forget_form_button}
            >
              确认修改
            </Button>
            <Link className={styles.forget_form_link} to="/login">登录</Link>
          </FormItem>
        </QueueAnim>
      </Form>
    );
  }
}

const Forget = Form.create()(ForgetForm);

export default connect()(Forget);
