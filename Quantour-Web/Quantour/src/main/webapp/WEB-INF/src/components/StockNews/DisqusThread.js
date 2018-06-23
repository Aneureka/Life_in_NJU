/**
 * Created by Hitigerzzz on 2017/6/9.
 */
import React, { PropTypes } from 'react';
import { canUseDOM } from 'fbjs/lib/ExecutionEnvironment';

const SHORTNAME = 'kevin807210561';
const WEBSITE_URL = 'http://qaquant.aneureka.cn';

function renderDisqus() {
  if (window.DISQUS === undefined) {
    const script = document.createElement('script');
    script.async = true;
    script.src = `https://${SHORTNAME}.disqus.com/embed.js`;
    script.setAttribute('data-timestamp', +new Date());
    document.getElementsByTagName('head')[0].appendChild(script);
  } else {
    window.DISQUS.reset({ reload: true });
  }
}

class DisqusThread extends React.Component {

  static propTypes = {
    id: PropTypes.string.isRequired,
    title: PropTypes.string.isRequired,
    path: PropTypes.string.isRequired,
    remote_auth_s3: PropTypes.string.isRequired,
  };

  componentDidMount() {
    renderDisqus();
  }

  shouldComponentUpdate(nextProps) {
    return this.props.id !== nextProps.id ||
      this.props.title !== nextProps.title ||
      this.props.path !== nextProps.path;
  }

  componentDidUpdate() {
    renderDisqus();
  }

  render() {
    const { id, title, path, remote_auth_s3, ...other } = this.props;
    if (canUseDOM) {
      /* eslint-disable camelcase */
      window.disqus_shortname = SHORTNAME;
      window.disqus_identifier = id;
      window.disqus_title = title;
      window.disqus_url = WEBSITE_URL + path;
      window.disqus_remote_auth_s3 = remote_auth_s3;
      window.disqus_api_key = 'qyhtVqwFZDInZsc869mBimY0cu3pvmHHXTKCtbhSvhBqkkmECnECv7APNf7Y5CrK';
      /* eslint-enable camelcase */
    }

    return <div {...other} id="disqus_thread" style={{ padding: '20px' }} />;
  }

}

export default DisqusThread;
