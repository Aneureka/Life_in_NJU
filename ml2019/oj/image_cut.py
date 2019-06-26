from PIL import Image
import numpy as np
from sklearn.mixture import GaussianMixture

#******** Begin *********#
im = Image.open('./step3/image/test.jpg')
img = np.array(im)
shape = img.shape
img = img.reshape(-1, 3)

gmm = GaussianMixture(3, max_iter=100)
gmm.fit(img)
pred = gmm.predict(img)

img[pred == 0, :] = [255, 255, 0]
img[pred == 1, :] = [0, 0, 255]
img[pred == 2, :] = [0, 255, 0]

img = img.reshape(shape)

im = Image.fromarray(img.astype('uint8'))
im.save('./step3/dump/result.jpg')
#********* End *********#