//
//  Post.swift
//  ClassiCal
//
//  Created by 曾 锦涛 on 3/6/15.
//  Copyright (c) 2015 CS 307 Team 8. All rights reserved.
//

import Foundation
import UIKit

class Post: NSObject {
    var title: String
    var content: String
    init(title: String, content: String) {
        self.title = title
        self.content = content
    }
}