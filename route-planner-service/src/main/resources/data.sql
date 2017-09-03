INSERT INTO route_info(id,busId,busNo,origin,destination,timeatorigin, legSeqNo, maxTravelTimeMinutes, distanceInKm, stageSeqId, busType, disabled) VALUES (1, 1, '19A', 'sholinganallur', 'ptc', 700, 1, 10, 5, 1, 'VolvoAC', false);
INSERT INTO route_info(id,busId,busNo,origin,destination,timeatorigin, legSeqNo, maxTravelTimeMinutes, distanceInKm, stageSeqId, busType, disabled) VALUES (2, 1, '19A', 'ptc', 'okkiyam', 710, 2, 5, 2, 1, 'VolvoAC', false);
INSERT INTO route_info(id,busId,busNo,origin,destination,timeatorigin, legSeqNo, maxTravelTimeMinutes, distanceInKm, stageSeqId, busType, disabled) VALUES (3, 1, '19A', 'okkiyam', 'thoraipakkam', 720, 3, 5, 2, 1, 'VolvoAC', false);
INSERT INTO route_info(id,busId,busNo,origin,destination,timeatorigin, legSeqNo, maxTravelTimeMinutes, distanceInKm, stageSeqId, busType, disabled) VALUES (5, 1, '19A', 'thoraipakkam', 'perungudi', 725, 4, 6, 5, 1, 'VolvoAC', false);
INSERT INTO route_info(id,busId,busNo,origin,destination,timeatorigin, legSeqNo, maxTravelTimeMinutes, distanceInKm, stageSeqId, busType, disabled) VALUES (6, 1, '19A', 'perungudi', 'kandanchavadi', 735, 5, 3, 2, 1, 'VolvoAC', false);
INSERT INTO route_info(id,busId,busNo,origin,destination,timeatorigin, legSeqNo, maxTravelTimeMinutes, distanceInKm, stageSeqId, busType, disabled) VALUES (7, 1, '19A', 'kandanchavadi', 'srp', 741, 6, 6, 4, 2, 'VolvoAC', false);
INSERT INTO route_info(id,busId,busNo,origin,destination,timeatorigin, legSeqNo, maxTravelTimeMinutes, distanceInKm, stageSeqId, busType, disabled) VALUES (8, 1, '19A', 'srp', 'tidel', 749, 7, 7, 7, 2, 'VolvoAC', false);
INSERT INTO route_info(id,busId,busNo,origin,destination,timeatorigin, legSeqNo, maxTravelTimeMinutes, distanceInKm, stageSeqId, busType, disabled) VALUES (9, 1, '19A', 'tidel', 'indranagar', 755, 8, 4, 3, 2, 'VolvoAC', false);
INSERT INTO route_info(id,busId,busNo,origin,destination,timeatorigin, legSeqNo, maxTravelTimeMinutes, distanceInKm, stageSeqId, busType, disabled) VALUES (10, 1, '19A', 'indranagar', 'adyar', 801, 9, 5, 8, 2, 'VolvoAC', false);


INSERT INTO stage_pricing(id,busType,farePerStage) VALUES (1, 'VolvoAC', 20);
