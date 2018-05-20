import cPickle as pickle

# Create a dictionary whose keys will be the 36 cubic space groups
# Each of those keys will itself be a list whose entries are strings corresponding to wyckoff multiplicities/letters of the group

cubicspacegroups={}
# Cubic space group: P23
# Total number of wyckoff sites for this group: 10
# -------------------------------------
cubicspacegroups['P23']=[]
cubicspacegroups['P23'].append("12j")
cubicspacegroups['P23'].append("6i")
cubicspacegroups['P23'].append("6h")
cubicspacegroups['P23'].append("6g")
cubicspacegroups['P23'].append("6f")
cubicspacegroups['P23'].append("4e")
cubicspacegroups['P23'].append("3d")
cubicspacegroups['P23'].append("3c")
cubicspacegroups['P23'].append("1b")
cubicspacegroups['P23'].append("1a")


# Cubic space group: F23
# Total number of wyckoff sites for this group: 8
# -------------------------------------
cubicspacegroups['F23']=[]
cubicspacegroups['F23'].append("48h")
cubicspacegroups['F23'].append("24g")
cubicspacegroups['F23'].append("24f")
cubicspacegroups['F23'].append("16e")
cubicspacegroups['F23'].append("4d")
cubicspacegroups['F23'].append("4c")
cubicspacegroups['F23'].append("4b")
cubicspacegroups['F23'].append("4a")


# Cubic space group: I23
# Total number of wyckoff sites for this group: 6
# -------------------------------------
cubicspacegroups['I23']=[]
cubicspacegroups['I23'].append("24f")
cubicspacegroups['I23'].append("12e")
cubicspacegroups['I23'].append("12d")
cubicspacegroups['I23'].append("8c")
cubicspacegroups['I23'].append("6b")
cubicspacegroups['I23'].append("2a")


# Cubic space group: P2_13
# Total number of wyckoff sites for this group: 2
# -------------------------------------
cubicspacegroups['P2_13']=[]
cubicspacegroups['P2_13'].append("12b")
cubicspacegroups['P2_13'].append("4a")


# Cubic space group: I2_13
# Total number of wyckoff sites for this group: 3
# -------------------------------------
cubicspacegroups['I2_13']=[]
cubicspacegroups['I2_13'].append("24c")
cubicspacegroups['I2_13'].append("12b")
cubicspacegroups['I2_13'].append("8a")


# Cubic space group: Pm-3
# Total number of wyckoff sites for this group: 12
# -------------------------------------
cubicspacegroups['Pm-3']=[]
cubicspacegroups['Pm-3'].append("24l")
cubicspacegroups['Pm-3'].append("12k")
cubicspacegroups['Pm-3'].append("12j")
cubicspacegroups['Pm-3'].append("8i")
cubicspacegroups['Pm-3'].append("6h")
cubicspacegroups['Pm-3'].append("6g")
cubicspacegroups['Pm-3'].append("6f")
cubicspacegroups['Pm-3'].append("6e")
cubicspacegroups['Pm-3'].append("3d")
cubicspacegroups['Pm-3'].append("3c")
cubicspacegroups['Pm-3'].append("1b")
cubicspacegroups['Pm-3'].append("1a")


# Cubic space group: Pn-3
# Total number of wyckoff sites for this group: 8
# -------------------------------------
cubicspacegroups['Pn-3']=[]
cubicspacegroups['Pn-3'].append("24h")
cubicspacegroups['Pn-3'].append("12g")
cubicspacegroups['Pn-3'].append("12f")
cubicspacegroups['Pn-3'].append("8e")
cubicspacegroups['Pn-3'].append("6d")
cubicspacegroups['Pn-3'].append("4c")
cubicspacegroups['Pn-3'].append("4b")
cubicspacegroups['Pn-3'].append("2a")


# Cubic space group: Fm-3
# Total number of wyckoff sites for this group: 9
# -------------------------------------
cubicspacegroups['Fm-3']=[]
cubicspacegroups['Fm-3'].append("96i")
cubicspacegroups['Fm-3'].append("48h")
cubicspacegroups['Fm-3'].append("48g")
cubicspacegroups['Fm-3'].append("32f")
cubicspacegroups['Fm-3'].append("24e")
cubicspacegroups['Fm-3'].append("24d")
cubicspacegroups['Fm-3'].append("8c")
cubicspacegroups['Fm-3'].append("4b")
cubicspacegroups['Fm-3'].append("4a")


# Cubic space group: Fd-3
# Total number of wyckoff sites for this group: 7
# -------------------------------------
cubicspacegroups['Fd-3']=[]
cubicspacegroups['Fd-3'].append("96g")
cubicspacegroups['Fd-3'].append("48f")
cubicspacegroups['Fd-3'].append("32e")
cubicspacegroups['Fd-3'].append("16d")
cubicspacegroups['Fd-3'].append("16c")
cubicspacegroups['Fd-3'].append("8b")
cubicspacegroups['Fd-3'].append("8a")


# Cubic space group: Im-3
# Total number of wyckoff sites for this group: 8
# -------------------------------------
cubicspacegroups['Im-3']=[]
cubicspacegroups['Im-3'].append("48h")
cubicspacegroups['Im-3'].append("24g")
cubicspacegroups['Im-3'].append("16f")
cubicspacegroups['Im-3'].append("12e")
cubicspacegroups['Im-3'].append("12d")
cubicspacegroups['Im-3'].append("8c")
cubicspacegroups['Im-3'].append("6b")
cubicspacegroups['Im-3'].append("2a")


# Cubic space group: Pa-3
# Total number of wyckoff sites for this group: 4
# -------------------------------------
cubicspacegroups['Pa-3']=[]
cubicspacegroups['Pa-3'].append("24d")
cubicspacegroups['Pa-3'].append("8c")
cubicspacegroups['Pa-3'].append("4b")
cubicspacegroups['Pa-3'].append("4a")


# Cubic space group: Ia-3
# Total number of wyckoff sites for this group: 5
# -------------------------------------
cubicspacegroups['Ia-3']=[]
cubicspacegroups['Ia-3'].append("48e")
cubicspacegroups['Ia-3'].append("24d")
cubicspacegroups['Ia-3'].append("16c")
cubicspacegroups['Ia-3'].append("8b")
cubicspacegroups['Ia-3'].append("8a")


# Cubic space group: P432
# Total number of wyckoff sites for this group: 11
# -------------------------------------
cubicspacegroups['P432']=[]
cubicspacegroups['P432'].append("24k")
cubicspacegroups['P432'].append("12j")
cubicspacegroups['P432'].append("12i")
cubicspacegroups['P432'].append("12h")
cubicspacegroups['P432'].append("8g")
cubicspacegroups['P432'].append("6f")
cubicspacegroups['P432'].append("6e")
cubicspacegroups['P432'].append("3d")
cubicspacegroups['P432'].append("3c")
cubicspacegroups['P432'].append("1b")
cubicspacegroups['P432'].append("1a")


# Cubic space group: P4_232
# Total number of wyckoff sites for this group: 13
# -------------------------------------
cubicspacegroups['P4_232']=[]
cubicspacegroups['P4_232'].append("24m")
cubicspacegroups['P4_232'].append("12l")
cubicspacegroups['P4_232'].append("12k")
cubicspacegroups['P4_232'].append("12j")
cubicspacegroups['P4_232'].append("12i")
cubicspacegroups['P4_232'].append("12h")
cubicspacegroups['P4_232'].append("8g")
cubicspacegroups['P4_232'].append("6f")
cubicspacegroups['P4_232'].append("6e")
cubicspacegroups['P4_232'].append("6d")
cubicspacegroups['P4_232'].append("4c")
cubicspacegroups['P4_232'].append("4b")
cubicspacegroups['P4_232'].append("2a")


# Cubic space group: F432
# Total number of wyckoff sites for this group: 10
# -------------------------------------
cubicspacegroups['F432']=[]
cubicspacegroups['F432'].append("96j")
cubicspacegroups['F432'].append("48i")
cubicspacegroups['F432'].append("48h")
cubicspacegroups['F432'].append("48g")
cubicspacegroups['F432'].append("32f")
cubicspacegroups['F432'].append("24e")
cubicspacegroups['F432'].append("24d")
cubicspacegroups['F432'].append("8c")
cubicspacegroups['F432'].append("4b")
cubicspacegroups['F432'].append("4a")


# Cubic space group: F4_132
# Total number of wyckoff sites for this group: 8
# -------------------------------------
cubicspacegroups['F4_132']=[]
cubicspacegroups['F4_132'].append("96h")
cubicspacegroups['F4_132'].append("48g")
cubicspacegroups['F4_132'].append("48f")
cubicspacegroups['F4_132'].append("32e")
cubicspacegroups['F4_132'].append("16d")
cubicspacegroups['F4_132'].append("16c")
cubicspacegroups['F4_132'].append("8b")
cubicspacegroups['F4_132'].append("8a")


# Cubic space group: I432
# Total number of wyckoff sites for this group: 10
# -------------------------------------
cubicspacegroups['I432']=[]
cubicspacegroups['I432'].append("48j")
cubicspacegroups['I432'].append("24i")
cubicspacegroups['I432'].append("24h")
cubicspacegroups['I432'].append("24g")
cubicspacegroups['I432'].append("16f")
cubicspacegroups['I432'].append("12e")
cubicspacegroups['I432'].append("12d")
cubicspacegroups['I432'].append("8c")
cubicspacegroups['I432'].append("6b")
cubicspacegroups['I432'].append("2a")


# Cubic space group: P4_332
# Total number of wyckoff sites for this group: 5
# -------------------------------------
cubicspacegroups['P4_332']=[]
cubicspacegroups['P4_332'].append("24e")
cubicspacegroups['P4_332'].append("12d")
cubicspacegroups['P4_332'].append("8c")
cubicspacegroups['P4_332'].append("4b")
cubicspacegroups['P4_332'].append("4a")


# Cubic space group: P4_132
# Total number of wyckoff sites for this group: 5
# -------------------------------------
cubicspacegroups['P4_132']=[]
cubicspacegroups['P4_132'].append("24e")
cubicspacegroups['P4_132'].append("12d")
cubicspacegroups['P4_132'].append("8c")
cubicspacegroups['P4_132'].append("4b")
cubicspacegroups['P4_132'].append("4a")


# Cubic space group: I4_132
# Total number of wyckoff sites for this group: 9
# -------------------------------------
cubicspacegroups['I4_132']=[]
cubicspacegroups['I4_132'].append("48i")
cubicspacegroups['I4_132'].append("24h")
cubicspacegroups['I4_132'].append("24g")
cubicspacegroups['I4_132'].append("24f")
cubicspacegroups['I4_132'].append("16e")
cubicspacegroups['I4_132'].append("12d")
cubicspacegroups['I4_132'].append("12c")
cubicspacegroups['I4_132'].append("8b")
cubicspacegroups['I4_132'].append("8a")


# Cubic space group: P-43m
# Total number of wyckoff sites for this group: 10
# -------------------------------------
cubicspacegroups['P-43m']=[]
cubicspacegroups['P-43m'].append("24j")
cubicspacegroups['P-43m'].append("12i")
cubicspacegroups['P-43m'].append("12h")
cubicspacegroups['P-43m'].append("6g")
cubicspacegroups['P-43m'].append("6f")
cubicspacegroups['P-43m'].append("4e")
cubicspacegroups['P-43m'].append("3d")
cubicspacegroups['P-43m'].append("3c")
cubicspacegroups['P-43m'].append("1b")
cubicspacegroups['P-43m'].append("1a")


# Cubic space group: F-43m
# Total number of wyckoff sites for this group: 9
# -------------------------------------
cubicspacegroups['F-43m']=[]
cubicspacegroups['F-43m'].append("96i")
cubicspacegroups['F-43m'].append("48h")
cubicspacegroups['F-43m'].append("24g")
cubicspacegroups['F-43m'].append("24f")
cubicspacegroups['F-43m'].append("16e")
cubicspacegroups['F-43m'].append("4d")
cubicspacegroups['F-43m'].append("4c")
cubicspacegroups['F-43m'].append("4b")
cubicspacegroups['F-43m'].append("4a")


# Cubic space group: I-43m
# Total number of wyckoff sites for this group: 8
# -------------------------------------
cubicspacegroups['I-43m']=[]
cubicspacegroups['I-43m'].append("48h")
cubicspacegroups['I-43m'].append("24g")
cubicspacegroups['I-43m'].append("24f")
cubicspacegroups['I-43m'].append("12e")
cubicspacegroups['I-43m'].append("12d")
cubicspacegroups['I-43m'].append("8c")
cubicspacegroups['I-43m'].append("6b")
cubicspacegroups['I-43m'].append("2a")


# Cubic space group: P-43n
# Total number of wyckoff sites for this group: 9
# -------------------------------------
cubicspacegroups['P-43n']=[]
cubicspacegroups['P-43n'].append("24i")
cubicspacegroups['P-43n'].append("12h")
cubicspacegroups['P-43n'].append("12g")
cubicspacegroups['P-43n'].append("12f")
cubicspacegroups['P-43n'].append("8e")
cubicspacegroups['P-43n'].append("6d")
cubicspacegroups['P-43n'].append("6c")
cubicspacegroups['P-43n'].append("6b")
cubicspacegroups['P-43n'].append("2a")


# Cubic space group: F-43c
# Total number of wyckoff sites for this group: 8
# -------------------------------------
cubicspacegroups['F-43c']=[]
cubicspacegroups['F-43c'].append("96h")
cubicspacegroups['F-43c'].append("48g")
cubicspacegroups['F-43c'].append("48f")
cubicspacegroups['F-43c'].append("32e")
cubicspacegroups['F-43c'].append("24d")
cubicspacegroups['F-43c'].append("24c")
cubicspacegroups['F-43c'].append("8b")
cubicspacegroups['F-43c'].append("8a")


# Cubic space group: I-43d
# Total number of wyckoff sites for this group: 5
# -------------------------------------
cubicspacegroups['I-43d']=[]
cubicspacegroups['I-43d'].append("48e")
cubicspacegroups['I-43d'].append("24d")
cubicspacegroups['I-43d'].append("16c")
cubicspacegroups['I-43d'].append("12b")
cubicspacegroups['I-43d'].append("12a")


# Cubic space group: Pm-3m
# Total number of wyckoff sites for this group: 14
# -------------------------------------
cubicspacegroups['Pm-3m']=[]
cubicspacegroups['Pm-3m'].append("48n")
cubicspacegroups['Pm-3m'].append("24m")
cubicspacegroups['Pm-3m'].append("24l")
cubicspacegroups['Pm-3m'].append("24k")
cubicspacegroups['Pm-3m'].append("12j")
cubicspacegroups['Pm-3m'].append("12i")
cubicspacegroups['Pm-3m'].append("12h")
cubicspacegroups['Pm-3m'].append("8g")
cubicspacegroups['Pm-3m'].append("6f")
cubicspacegroups['Pm-3m'].append("6e")
cubicspacegroups['Pm-3m'].append("3d")
cubicspacegroups['Pm-3m'].append("3c")
cubicspacegroups['Pm-3m'].append("1b")
cubicspacegroups['Pm-3m'].append("1a")


# Cubic space group: Pn-3n
# Total number of wyckoff sites for this group: 9
# -------------------------------------
cubicspacegroups['Pn-3n']=[]
cubicspacegroups['Pn-3n'].append("48i")
cubicspacegroups['Pn-3n'].append("24h")
cubicspacegroups['Pn-3n'].append("24g")
cubicspacegroups['Pn-3n'].append("16f")
cubicspacegroups['Pn-3n'].append("12e")
cubicspacegroups['Pn-3n'].append("12d")
cubicspacegroups['Pn-3n'].append("8c")
cubicspacegroups['Pn-3n'].append("6b")
cubicspacegroups['Pn-3n'].append("2a")


# Cubic space group: Pm-3n
# Total number of wyckoff sites for this group: 12
# -------------------------------------
cubicspacegroups['Pm-3n']=[]
cubicspacegroups['Pm-3n'].append("48l")
cubicspacegroups['Pm-3n'].append("24k")
cubicspacegroups['Pm-3n'].append("24j")
cubicspacegroups['Pm-3n'].append("16i")
cubicspacegroups['Pm-3n'].append("12h")
cubicspacegroups['Pm-3n'].append("12g")
cubicspacegroups['Pm-3n'].append("12f")
cubicspacegroups['Pm-3n'].append("8e")
cubicspacegroups['Pm-3n'].append("6d")
cubicspacegroups['Pm-3n'].append("6c")
cubicspacegroups['Pm-3n'].append("6b")
cubicspacegroups['Pm-3n'].append("2a")


# Cubic space group: Pn-3m
# Total number of wyckoff sites for this group: 12
# -------------------------------------
cubicspacegroups['Pn-3m']=[]
cubicspacegroups['Pn-3m'].append("48l")
cubicspacegroups['Pn-3m'].append("24k")
cubicspacegroups['Pn-3m'].append("24j")
cubicspacegroups['Pn-3m'].append("24i")
cubicspacegroups['Pn-3m'].append("24h")
cubicspacegroups['Pn-3m'].append("12g")
cubicspacegroups['Pn-3m'].append("12f")
cubicspacegroups['Pn-3m'].append("8e")
cubicspacegroups['Pn-3m'].append("6d")
cubicspacegroups['Pn-3m'].append("4c")
cubicspacegroups['Pn-3m'].append("4b")
cubicspacegroups['Pn-3m'].append("2a")


# Cubic space group: Fm-3m
# Total number of wyckoff sites for this group: 12
# -------------------------------------
cubicspacegroups['Fm-3m']=[]
cubicspacegroups['Fm-3m'].append("192l")
cubicspacegroups['Fm-3m'].append("96k")
cubicspacegroups['Fm-3m'].append("96j")
cubicspacegroups['Fm-3m'].append("48i")
cubicspacegroups['Fm-3m'].append("48h")
cubicspacegroups['Fm-3m'].append("48g")
cubicspacegroups['Fm-3m'].append("32f")
cubicspacegroups['Fm-3m'].append("24e")
cubicspacegroups['Fm-3m'].append("24d")
cubicspacegroups['Fm-3m'].append("8c")
cubicspacegroups['Fm-3m'].append("4b")
cubicspacegroups['Fm-3m'].append("4a")


# Cubic space group: Fm-3c
# Total number of wyckoff sites for this group: 10
# -------------------------------------
cubicspacegroups['Fm-3c']=[]
cubicspacegroups['Fm-3c'].append("192j")
cubicspacegroups['Fm-3c'].append("96i")
cubicspacegroups['Fm-3c'].append("96h")
cubicspacegroups['Fm-3c'].append("64g")
cubicspacegroups['Fm-3c'].append("48f")
cubicspacegroups['Fm-3c'].append("48e")
cubicspacegroups['Fm-3c'].append("24d")
cubicspacegroups['Fm-3c'].append("24c")
cubicspacegroups['Fm-3c'].append("8b")
cubicspacegroups['Fm-3c'].append("8a")


# Cubic space group: Fd-3m
# Total number of wyckoff sites for this group: 9
# -------------------------------------
cubicspacegroups['Fd-3m']=[]
cubicspacegroups['Fd-3m'].append("192i")
cubicspacegroups['Fd-3m'].append("96h")
cubicspacegroups['Fd-3m'].append("96g")
cubicspacegroups['Fd-3m'].append("48f")
cubicspacegroups['Fd-3m'].append("32e")
cubicspacegroups['Fd-3m'].append("16d")
cubicspacegroups['Fd-3m'].append("16c")
cubicspacegroups['Fd-3m'].append("8b")
cubicspacegroups['Fd-3m'].append("8a")


# Cubic space group: Fd-3c
# Total number of wyckoff sites for this group: 8
# -------------------------------------
cubicspacegroups['Fd-3c']=[]
cubicspacegroups['Fd-3c'].append("192h")
cubicspacegroups['Fd-3c'].append("96g")
cubicspacegroups['Fd-3c'].append("96f")
cubicspacegroups['Fd-3c'].append("64e")
cubicspacegroups['Fd-3c'].append("48d")
cubicspacegroups['Fd-3c'].append("32c")
cubicspacegroups['Fd-3c'].append("32b")
cubicspacegroups['Fd-3c'].append("16a")


# Cubic space group: Im-3m
# Total number of wyckoff sites for this group: 12
# -------------------------------------
cubicspacegroups['Im-3m']=[]
cubicspacegroups['Im-3m'].append("96l")
cubicspacegroups['Im-3m'].append("48k")
cubicspacegroups['Im-3m'].append("48j")
cubicspacegroups['Im-3m'].append("48i")
cubicspacegroups['Im-3m'].append("24h")
cubicspacegroups['Im-3m'].append("24g")
cubicspacegroups['Im-3m'].append("16f")
cubicspacegroups['Im-3m'].append("12e")
cubicspacegroups['Im-3m'].append("12d")
cubicspacegroups['Im-3m'].append("8c")
cubicspacegroups['Im-3m'].append("6b")
cubicspacegroups['Im-3m'].append("2a")


# Cubic space group: Ia-3d
# Total number of wyckoff sites for this group: 8
# -------------------------------------
cubicspacegroups['Ia-3d']=[]
cubicspacegroups['Ia-3d'].append("96h")
cubicspacegroups['Ia-3d'].append("48g")
cubicspacegroups['Ia-3d'].append("48f")
cubicspacegroups['Ia-3d'].append("32e")
cubicspacegroups['Ia-3d'].append("24d")
cubicspacegroups['Ia-3d'].append("24c")
cubicspacegroups['Ia-3d'].append("16b")
cubicspacegroups['Ia-3d'].append("16a")


pickle.dump(cubicspacegroups,open("cubicspacegroups.pkl","wb"))
