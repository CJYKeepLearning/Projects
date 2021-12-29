import { LOGIN_STATUS, LOGOUT_STATUS} from "../common/Global";

export default (state = false, action) => {
  switch (action.type) {
    case LOGIN_STATUS:
      return true;
    case LOGOUT_STATUS:
      return false;
    default:
      return false;
  }
};

export const mapStateToProps = state => ({
  isLogin: state.isLogin
});

export const login_success = () => {
  return {
    type: LOGIN_STATUS
  }
}

export const login_fail = () => ({type: LOGOUT_STATUS})

