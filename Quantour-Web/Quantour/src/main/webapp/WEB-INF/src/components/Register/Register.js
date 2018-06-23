/**
 * Created by Hitigerzzz on 2017/5/29.
 */
import React from 'react';
import { Form, Input, Tooltip, Icon, Select, Button } from 'antd';
import { connect } from 'dva';
import { Link } from 'dva/router';
import QueueAnim from 'rc-queue-anim';
import styles from './Register.less';

const FormItem = Form.Item;
const Option = Select.Option;


class RegistrationForm extends React.Component {
  state = {
    confirmDirty: false,
    autoCompleteResult: [],
    usernameStatus: '',
    usernameHelp: '',
    emailStatus: '',
    emailHelp: '',
    emailWrongStatus: '',
    emailWrongHelp: '',
    phoneStatus: '',
    phoneHelp: '',
    phoneWrongStatus: '',
    phoneWrongHelp: '',
  };
  componentWillReceiveProps(nextProps) {
    if (nextProps.status !== undefined) {
      this.setState({
        usernameStatus: nextProps.status === 501 ? 'error' : '',
        usernameHelp: nextProps.status === 501 ? '用户名已存在！' : '',
        emailStatus: nextProps.status === 502 ? 'error' : '',
        emailHelp: nextProps.status === 502 ? '邮箱已被注册！' : '',
        phoneStatus: nextProps.status === 503 ? 'error' : '',
        phoneHelp: nextProps.status === 503 ? '手机号已被注册！' : '',
        emailWrongStatus: nextProps.status === 508 ? 'error' : '',
        emailWrongHelp: nextProps.status === 508 ? '邮箱格式不正确！' : '',
        phoneWrongStatus: nextProps.status === 509 ? 'error' : '',
        phoneWrongHelp: nextProps.status === 509 ? '手机号格式不正确！' : '',
      });
    }
  }
  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFieldsAndScroll((err, values) => {
      if (!err) {
        const data = JSON.parse(JSON.stringify(values));
        const newUser = {
          username: data.username,
          password: data.password,
          emailAddress: data.email,
          phoneNumber: data.phone,
        };
        this.props.dispatch({
          type: 'users/create',
          payload: JSON.parse(JSON.stringify(newUser)),
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
      <Form onSubmit={this.handleSubmit} className={styles.register_form}>
        <QueueAnim
          duration={1000}
        >
          <FormItem
            key="anim2"
            {...formItemLayout}
            label={(
              <span>
              用户名&nbsp;
                <Tooltip title="What do you want other to call you?">
                  <Icon type="question-circle-o" />
                </Tooltip>
              </span>
            )}
            hasFeedback
            validateStatus={this.state.usernameStatus}
            help={this.state.usernameHelp}
          >
            {getFieldDecorator('username', {
              rules: [{ required: true, message: '请输入用户名！', whitespace: true }],
            })(
              <Input />,
            )}
          </FormItem>
          <FormItem
            key="anim5"
            {...formItemLayout}
            label="手机号码"
            validateStatus={this.state.phoneStatus === '' ?
              (this.state.phoneWrongStatus === '' ? '' : 'error') : 'error'}
            help={this.state.phoneStatus === '' ?
              (this.state.phoneWrongStatus === '' ? '' : this.state.phoneWrongHelp) : this.state.phoneHelp}
          >
            {getFieldDecorator('phone', {
              rules: [{ required: true, message: '请输入手机号码！' }],
            })(
              <Input />,
            )}
          </FormItem>
          <FormItem
            {...formItemLayout}
            label="邮箱"
            hasFeedback
            key="anim1"
            validateStatus={this.state.emailStatus === '' ?
              (this.state.emailWrongStatus === '' ? '' : 'error') : 'error'}
            help={this.state.emailStatus === '' ?
              (this.state.emailWrongStatus === '' ? '' : this.state.emailWrongHelp) : this.state.emailHelp}
          >
            {getFieldDecorator('email', {
              rules: [{
                required: true, message: '请输入你的邮箱！',
              }],
            })(
              <Input />,
            )}
          </FormItem>
          <FormItem
            key="anim3"
            {...formItemLayout}
            label="密码"
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
            label="确认密码"
            hasFeedback
          >
            {getFieldDecorator('confirm', {
              rules: [{
                required: true, message: '请确认密码！',
              }, {
                validator: this.checkPassword,
              }],
            })(
              <Input type="password" onBlur={this.handleConfirmBlur} />,
            )}
          </FormItem>
          <FormItem {...tailFormItemLayout} key="anim6" >
            <Button
              type="primary"
              htmlType="submit"
              size="large"
              className={styles.register_form_button}
            >
              注册
            </Button>
            <Link className={styles.register_form_link} to="/login">已有账号，登录</Link>
          </FormItem>
        </QueueAnim>
      </Form>
    );
  }
}

const WrappedRegistrationForm = Form.create()(RegistrationForm);

function mapStateToProps(state) {
  const status = state.users;
  return status;
}

export default connect(mapStateToProps)(WrappedRegistrationForm);
