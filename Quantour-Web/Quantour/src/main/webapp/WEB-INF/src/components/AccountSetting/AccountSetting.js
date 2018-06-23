/**
 * Created by wyj on 2017/6/4.
 * description:
 */
import React from 'react';
import { Form, Input, Button } from 'antd';
import { connect } from 'dva';

const FormItem = Form.Item;


class RegistrationForm extends React.Component {
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
          password: data.old,
          newPassword: data.password,
        };
        this.props.dispatch({
          type: 'users/changePassword',
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
      callback('Two passwords that you enter is inconsistent!');
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
      <Form onSubmit={this.handleSubmit}>
        <FormItem
          {...formItemLayout}
          label="旧密码"
          hasFeedback
        >
          {getFieldDecorator('old', {
            rules: [{
              required: true, message: 'Please input your password!',
            }],
          })(
            <Input type="password" />,
          )}
        </FormItem>
        <FormItem
          {...formItemLayout}
          label="新密码"
          hasFeedback
        >
          {getFieldDecorator('password', {
            rules: [{
              required: true, message: 'Please input your password!',
            }, {
              validator: this.checkConfirm,
            }],
          })(
            <Input type="password" />,
          )}
        </FormItem>
        <FormItem
          {...formItemLayout}
          label="确认新密码"
          hasFeedback
        >
          {getFieldDecorator('confirm', {
            rules: [{
              required: true, message: 'Please confirm your password!',
            }, {
              validator: this.checkPassword,
            }],
          })(
            <Input type="password" onBlur={this.handleConfirmBlur} />,
          )}
        </FormItem>
        <FormItem {...tailFormItemLayout}>
          <Button type="primary" htmlType="submit" size="large">确认修改</Button>
          {/* <span*/}
          {/* style={{ clear: 'both',*/}
          {/* color: '#e84545',*/}
          {/* textAlign: 'center',*/}
          {/* display: `${this.props.changePasswordFlag}` }}*/}
          {/* >密码修改错误，请检查你的原密码</span>*/}
          {/* <span*/}
          {/* style={{ clear: 'both',*/}
          {/* color: '#3db8c1',*/}
          {/* textAlign: 'center',*/}
          {/* display: `${this.props.changePasswordFlag}` }}*/}
          {/* >密码修改成功!</span>*/}
        </FormItem>
      </Form>
    );
  }
}

const WrappedRegistrationForm = Form.create()(RegistrationForm);

// function mapStateToProps(state) {
//   const { changePasswordFlag } = state.users;
//   return { changePasswordFlag };
// }

export default connect()(WrappedRegistrationForm);
