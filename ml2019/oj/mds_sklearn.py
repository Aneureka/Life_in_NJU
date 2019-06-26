from sklearn.manifold import MDS


def mds(data, d):
    mds = MDS(n_components=2)
    Z = mds.fit_transform(data)
    return Z