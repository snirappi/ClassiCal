//
//  Reply.swift
//  ClassiCal
//
//  Created by ZengJintao on 3/18/15.
//  Copyright (c) 2015 CS 307 Team 8. All rights reserved.
//

import Foundation
import UIKit

class Reply: NSObject {
    var name: String
    var content: String
    var time: String
    var like: Int
    init(name: String, content: String, time: String) {
        self.name = name
        self.content = content
        self.time = time
        self.like = Int(arc4random_uniform(7))
    }
}