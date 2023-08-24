import numpy as np
from scipy import sparse

eye = np.eye(4)
print("Numpy Array:\n{}".format(eye))

sparse_matrix = sparse.csr_matrix(eye)
print("Scipy CSR Matrix:\n".format(sparse_matrix))

data = np.ones(4)
print(data)

data = np.ones(4)

row_indices = np.arange(4)
col_indices = np.arange(4)

eye_coo = sparse.coo_matrix((data, (row_indices, col_indices)))

print("COO Expression:\n", eye_coo)
