// /**
//  * Created by wyj on 2017/6/4.
//  * description:
//  */
// import React from 'react';
// import { Form, Input, Tooltip, Icon, Select, Button, Cascader } from 'antd';
// import { connect } from 'dva';
// import styles from './PersonalInfoPane.less';
//
// const FormItem = Form.Item;
// const Option = Select.Option;
//
//
// class RegistrationForm extends React.Component {
//   state = {
//     confirmDirty: false,
//     autoCompleteResult: [],
//     showInfo: true,
//   };
//   handleSubmit = (e) => {
//     e.preventDefault();
//     this.props.form.validateFieldsAndScroll((err, values) => {
//       if (!err) {
//         console.log(values);
//       }
//     });
//     this.setState({
//       showInfo: true,
//     });
//   };
//   handleConfirmBlur = (e) => {
//     const value = e.target.value;
//     this.setState({ confirmDirty: this.state.confirmDirty || !!value });
//   };
//   checkPassword = (rule, value, callback) => {
//     const form = this.props.form;
//     if (value && value !== form.getFieldValue('password')) {
//       callback('Two passwords that you enter is inconsistent!');
//     } else {
//       callback();
//     }
//   };
//   checkConfirm = (rule, value, callback) => {
//     const form = this.props.form;
//     if (value && this.state.confirmDirty) {
//       form.validateFields(['confirm'], { force: true });
//     }
//     callback();
//   };
//
//   render() {
//     const { getFieldDecorator } = this.props.form;
//
//     const formItemLayout = {
//       labelCol: {
//         xs: { span: 24 },
//         sm: { span: 6 },
//       },
//       wrapperCol: {
//         xs: { span: 24 },
//         sm: { span: 14 },
//       },
//     };
//     const tailFormItemLayout = {
//       wrapperCol: {
//         xs: {
//           span: 24,
//           offset: 0,
//         },
//         sm: {
//           span: 14,
//           offset: 6,
//         },
//       },
//     };
//     const prefixSelector = getFieldDecorator('prefix', {
//       initialValue: '86',
//     })(
//       <Select className="icp-selector">
//         <Option value="86">+86</Option>
//       </Select>,
//     );
//
//     return (
//       <div>
//         <Form
//           onSubmit={this.handleSubmit}
//           style={{ display: this.state.showInfo ? 'none' : 'block' }}
//         >
//           <FormItem
//             {...formItemLayout}
//             label={(
//               <span>
//               用户名&nbsp;
//                 <Tooltip title="What do you want other to call you?">
//                   <Icon type="question-circle-o" />
//                 </Tooltip>
//               </span>
//             )}
//             hasFeedback
//           >
//             {getFieldDecorator('username', {
//               rules: [{ required: true, message: '请输入用户名!', whitespace: true }],
//               initialValue: this.props.userInfo.username,
//             })(
//               <Input />,
//             )}
//           </FormItem>
//           <FormItem
//             {...formItemLayout}
//             label="邮箱"
//             hasFeedback
//           >
//             {getFieldDecorator('email', {
//               rules: [{
//                 type: 'email', message: '邮箱格式不正确!',
//               }, {
//                 required: true, message: '请输入邮箱!',
//               }],
//               initialValue: this.props.userInfo.emailAddress,
//             })(
//               <Input />,
//             )}
//           </FormItem>
//           <FormItem
//             {...formItemLayout}
//             label="手机号"
//           >
//             {getFieldDecorator('phone', {
//               rules: [{ required: true, message: '请输入手机号!' }],
//               initialValue: this.props.userInfo.phoneNumber,
//             })(
//               <Input addonBefore={prefixSelector} />,
//             )}
//           </FormItem>
//           <FormItem {...tailFormItemLayout}>
//             <Button size="large">取消</Button>
//             <Button type="primary" htmlType="submit" size="large">保存基本信息</Button>
//           </FormItem>
//         </Form>
//
//       </div>
//     );
//   }
// }
//
// const WrappedRegistrationForm = Form.create()(RegistrationForm);
//
// function mapStateToProps(state) {
//   const userInfo = state.users;
//   return userInfo;
// }
//
// export default connect(mapStateToProps)(WrappedRegistrationForm);
