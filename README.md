# Arte-Five.O
ArTe Five O - Stencil Data Checker

This program checks the gerber data for apertures and creates a report file for that gerber file.

Currently supports:

				* Use a filedialog to select a gerber file,
				* Gets apertures from the file,
				* Calculates Area Ratio, Transfer Effeciency, ...
				* Creation of report file when aperture Area Ratio is smaller than 0.6.
				* Suggest a solderpaste type for µBGA's en ultra fine pitch
				
TODO:

				* External Polygon Grouper,
				* ARTE file creator,
				* Look into creating a GUI for the software,
				* Ability to create a .pdf (or similar) ARTE Report.
				* Add support for other file exports. Currently CircuitCam is working. Try to add support for Viewmate and GC-Cam.
